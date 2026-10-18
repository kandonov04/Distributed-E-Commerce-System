package endpoints;

import entities.Artikal;
import entities.Korpa;
import entities.Narudzbina;
import entities.Transakcija;
import entities.UKorpi;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import jms.JmsService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.annotation.*;

@Path("narudzbina")
@Stateless
public class NarudzbinaResource {

    @EJB
    JmsService jmsService;
    
    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PlacanjeRequest {
        @XmlElement public Integer idKor;
        @XmlElement public String adresa;
        @XmlElement public Integer idG;
        public PlacanjeRequest() {}
    }

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class MojeNarudzbineRequest {
        @XmlElement public Integer idKor;
        public MojeNarudzbineRequest() {}
    }
    
    // 14) PLATI
    @POST
    @Path("plati")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response plati(PlacanjeRequest req) throws Exception {
        if (req == null || req.idKor == null || req.adresa == null || req.idG == null){
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();
        }
        
        // 1) Dohvati korpu iz podsistema 2
        Serializable rezKorpaObj = jmsService.sendAndWait(24, String.valueOf(req.idKor));
        Korpa korpaObj = (Korpa) rezKorpaObj;

        if (korpaObj == null || korpaObj.getUkupnaCena().compareTo(BigDecimal.ZERO) == 0) {
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Korpa je prazna!").build();
        }
        BigDecimal ukupnaCena = korpaObj.getUkupnaCena();
        
        // 2) Dohvati stavke korpe
        Serializable rezStavke = jmsService.sendAndWait(19, String.valueOf(req.idKor));
        List<UKorpi> stavke = (List<UKorpi>) rezStavke;
        
        // 3) Za svaku stavku dohvati artikal i izracunaj jedinicnu cenu, i raspodeli dodelu po prodavcima
        StringBuilder stavkeStr = new StringBuilder();
        java.util.Map<Integer, BigDecimal> prodavciNovac = new java.util.HashMap<>();
        for (int i = 0; i < stavke.size(); i++) {
            UKorpi uk = stavke.get(i);
            Serializable rezArtikal = jmsService.sendAndWait(25, String.valueOf(uk.getUKorpiPK().getIdArt()));
            Artikal a = (Artikal) rezArtikal;
            if (a == null) {
                return Response.status(400).type(MediaType.APPLICATION_XML).entity("Greska pri dohvatanju artikla!").build();
            }
            BigDecimal jedinicnaCena = a.getCena();
            if (a.getPopust() != null && a.getPopust() > 0) {
                jedinicnaCena = a.getCena().multiply(BigDecimal.valueOf(1 - a.getPopust() / 100.0));
            }
            stavkeStr.append(uk.getUKorpiPK().getIdArt()).append(":")
                     .append(uk.getKolicina()).append(":")
                     .append(jedinicnaCena);
            if (i < stavke.size() - 1) {
                stavkeStr.append("|");
            }
            prodavciNovac.merge(a.getIdKor(), jedinicnaCena.multiply(BigDecimal.valueOf(uk.getKolicina())), BigDecimal::add);
        }
        String stavkeString = stavkeStr.toString();
        
        // 4) Dodaj novac prodavcima
        // 4b) Dodaj novac prodavcima
        for (java.util.Map.Entry<Integer, BigDecimal> entry : prodavciNovac.entrySet()) {
            jmsService.sendAndWait(31, entry.getKey() + "," + entry.getValue());
        }
        
        // 4) Skini novac korisniku
        String podaciSkidanje = req.idKor + "," + ukupnaCena;
        Serializable rezSkidanje = jmsService.sendAndWait(30, podaciSkidanje);
        if (rezSkidanje == null) {
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Nema dovoljno sredstava!").build();
        }
        
        // 5) Kreiraj narudzbinu u podsistemu 3
        String podaciNarudzbina = req.idKor + "," + ukupnaCena + "," + req.adresa + "," + req.idG;
        Serializable rezNarudzbina = jmsService.sendAndWait(14, podaciNarudzbina);
        Narudzbina narudzbina = (Narudzbina) rezNarudzbina;

        if (narudzbina == null) {
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Greska pri kreiranju narudzbine!").build();
        }
        
        // 6) Kreiraj stavke narudzbine u podsistemu 3
        String podaciStavke = narudzbina.getIdN() + "," + stavkeString;
        Serializable rezStavke2 = jmsService.sendAndWait(26, podaciStavke);
        if (rezStavke2 == null) {
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Greska pri kreiranju stavki!").build();
        }

        // 7) Kreiraj transakciju u podsistemu 3
        String podaciTransakcija = narudzbina.getIdN() + "," + ukupnaCena;
        Serializable rezTransakcija = jmsService.sendAndWait(27, podaciTransakcija);
        if (rezTransakcija == null) {
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Greska pri kreiranju transakcije!").build();
        }
        
        // 8) Resetuj korpu
        Serializable rezReset = jmsService.sendAndWait(28, String.valueOf(req.idKor));
        if (rezReset == null) {
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Greska pri resetovanju korpe!").build();
        }

        return Response.status(200).type(MediaType.APPLICATION_XML).entity("PLACENO").build();
        
    }

    // 21) SVE NARUDZBINE KORISNIKA
    @POST
    @Path("moje")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response getMoje(MojeNarudzbineRequest req) throws Exception {
        if (req == null || req.idKor == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();

        Serializable rez = jmsService.sendAndWait(21, String.valueOf(req.idKor));
        List<Narudzbina> lista = (List<Narudzbina>) rez;
        boolean ok = lista != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? new GenericEntity<List<Narudzbina>>(lista){} : "Greska!").build();
    }

    // 22) SVE NARUDZBINE
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAll() throws Exception {
        Serializable rez = jmsService.sendAndWait(22, null);
        List<Narudzbina> lista = (List<Narudzbina>) rez;
        boolean ok = lista != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? new GenericEntity<List<Narudzbina>>(lista){} : "Greska!").build();
    }

    // 23) SVE TRANSAKCIJE
    @GET
    @Path("transakcije")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllTransakcije() throws Exception {
        Serializable rez = jmsService.sendAndWait(23, null);
        List<Transakcija> lista = (List<Transakcija>) rez;
        boolean ok = lista != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? new GenericEntity<List<Transakcija>>(lista){} : "Greska!").build();
    }
}
