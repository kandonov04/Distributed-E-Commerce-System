package endpoints;

import entities.Korisnik;
import java.io.Serializable;
import java.util.List;
import jms.JmsService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.annotation.*;

@Path("korisnik")
@Stateless
public class KorisnikResource {

    @EJB
    JmsService jmsService;
    
    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class LoginRequest {
        @XmlElement public String korisnickoIme;
        @XmlElement public String sifra;
        public LoginRequest() {}
    }
    
    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CreateKorisnikRequest {
        @XmlElement public String korisnickoIme;
        @XmlElement public String sifra;
        @XmlElement public String ime;
        @XmlElement public String prezime;
        @XmlElement public String adresa;
        @XmlElement public Integer idG;
        public CreateKorisnikRequest() {}
    }

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class DodajNovacRequest {
        @XmlElement public String korisnickoIme;
        @XmlElement public Double iznos;
        public DodajNovacRequest() {}
    }

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PromeniAdresuRequest {
        @XmlElement public String korisnickoIme;
        @XmlElement public String adresa;
        @XmlElement public Integer idG;
        public PromeniAdresuRequest() {}
    }
    
    // 1) CHECK CREDENTIALS / LOGIN
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response login(LoginRequest req) throws Exception {
        Serializable rez = jmsService.sendAndWait(1, req.korisnickoIme + "," + req.sifra);
        Korisnik k = (Korisnik) rez;
        boolean ok = k != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();
    }
    

    // 3) KREIRAJ KORISNIKA
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response create(CreateKorisnikRequest req) throws Exception {
        if (req == null || req.korisnickoIme == null || req.sifra == null || req.ime == null || req.prezime == null || req.adresa == null || req.idG == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("GRESKA! Obavezna polja nedostaju!").build();

        String podaci = req.korisnickoIme + "," + req.sifra + "," + req.ime + "," + req.prezime + "," + req.adresa + "," + req.idG;
        System.out.println("PODACI: " + podaci);
        
        Serializable rez = jmsService.sendAndWait(3, podaci);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();

    }

    // 4) DODAJ NOVAC
    @POST
    @Path("dodajNovac")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response dodajNovac(DodajNovacRequest req) throws Exception {
        if (req == null || req.korisnickoIme == null || req.iznos == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("GRESKA! Obavezna polja nedostaju!").build();

        String podaci = req.korisnickoIme + "," + req.iznos;
        
        Serializable rez = jmsService.sendAndWait(4, podaci);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();
       
    }

    // 5) PROMENI ADRESU I GRAD
    @POST
    @Path("promeniAdresu")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response promeniAdresu(PromeniAdresuRequest req) throws Exception {
        if (req == null || req.korisnickoIme == null || req.idG == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("GRESKA! Obavezna polja nedostaju!").build();

        String podaci = req.korisnickoIme + "," + req.adresa + "," + req.idG;
        
        Serializable rez = jmsService.sendAndWait(5, podaci);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();

    }

    // 16) SVI KORISNICI
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAll() throws Exception {
        
        Serializable rez = jmsService.sendAndWait(16, null);
        List<Korisnik> lista = (List<Korisnik>) rez;
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? new GenericEntity<List<Korisnik>>(lista){} : "Greska!").build();
        
    }

}