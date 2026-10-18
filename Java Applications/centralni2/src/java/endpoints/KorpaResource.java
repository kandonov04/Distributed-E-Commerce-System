package endpoints;

import entities.UKorpi;
import java.util.List;
import java.io.Serializable;
import jms.JmsService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.annotation.*;

@Path("korpa")
@Stateless
public class KorpaResource {

    @EJB
    JmsService jmsService;

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class KorpaArtikalRequest {
        @XmlElement public Integer idKor;
        @XmlElement public Integer idArt;
        @XmlElement public Integer kolicina;
        public KorpaArtikalRequest() {}
    }

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GetKorpaRequest {
        @XmlElement public Integer idKor;
        public GetKorpaRequest() {}
    }

    // 10) DODAJ U KORPU
    @POST
    @Path("dodaj")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response dodaj(KorpaArtikalRequest req) throws Exception {
        if (req == null || req.idKor == null || req.idArt == null || req.kolicina == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();

        String podaci = req.idKor + "," + req.idArt + "," + req.kolicina;

        Serializable rez = jmsService.sendAndWait(10, podaci);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();
    }

    // 11) OBRISI IZ KORPE
    @POST
    @Path("obrisi")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response obrisi(KorpaArtikalRequest req) throws Exception {
        if (req == null || req.idKor == null || req.idArt == null || req.kolicina == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();

        String podaci = req.idKor + "," + req.idArt + "," + req.kolicina;

        Serializable rez = jmsService.sendAndWait(11, podaci);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();
    }

    // 19) DOHVATI SADRZAJ KORPE
    @POST
    @Path("get")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response getKorpa(GetKorpaRequest req) throws Exception {
        if (req == null || req.idKor == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();
        
        Serializable rez = jmsService.sendAndWait(19, String.valueOf(req.idKor));
        List<UKorpi> lista = (List<UKorpi>) rez;
        boolean ok = lista != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? new GenericEntity<List<UKorpi>>(lista){} : "Greska!").build();
        
    }
}
