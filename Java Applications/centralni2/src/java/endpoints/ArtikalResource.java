package endpoints;

import entities.Artikal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import jms.JmsService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.annotation.*;

@Path("artikal")
@Stateless
public class ArtikalResource {

    @EJB
    JmsService jmsService;

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CreateArtikalRequest {
        @XmlElement public String ime;
        @XmlElement public String opis;
        @XmlElement public BigDecimal cena;
        @XmlElement public Integer popust;
        @XmlElement public Integer idKor;
        @XmlElement public Integer idKat;
        public CreateArtikalRequest() {}
    }

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class UpdateCenaRequest {
        @XmlElement public Integer idArt;
        @XmlElement public BigDecimal cena;
        @XmlElement public Integer idKor;
        public UpdateCenaRequest() {}
    }

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class UpdatePopustRequest {
        @XmlElement public Integer idArt;
        @XmlElement public Integer popust;
        @XmlElement public Integer idKor;
        public UpdatePopustRequest() {}
    }

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class MojiArtikliRequest {
        @XmlElement public Integer idKor;
        public MojiArtikliRequest() {}
    }

    // 7) KREIRAJ ARTIKAL
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response create(CreateArtikalRequest req) throws Exception {
        if (req == null || req.ime == null || req.cena == null || req.idKor == null || req.idKat == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();

        String podaci = req.ime + "," + req.opis + "," + req.cena + "," + req.popust + "," + req.idKor + "," + req.idKat;

        Serializable rez = jmsService.sendAndWait(7, podaci);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();
    }

    // 8) PROMENI CENU
    @POST
    @Path("updateCena")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateCena(UpdateCenaRequest req) throws Exception {
        if (req == null || req.idArt == null || req.cena == null || req.idKor == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();

        String podaci = req.idArt + "," + req.cena + "," + req.idKor;

        Serializable rez = jmsService.sendAndWait(8, podaci);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();
    }

    // 9) PROMENI POPUST
    @POST
    @Path("updatePopust")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updatePopust(UpdatePopustRequest req) throws Exception {
        if (req == null || req.idArt == null || req.popust == null || req.idKor == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();

        String podaci = req.idArt + "," + req.popust + "," + req.idKor;

        Serializable rez = jmsService.sendAndWait(9, podaci);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();
    }

    // 18) SVI ARTIKLI KORISNIKA
    @POST
    @Path("moji")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response getMoji(MojiArtikliRequest req) throws Exception {
        if (req == null || req.idKor == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();

        Serializable rez = jmsService.sendAndWait(18, String.valueOf(req.idKor));
        List<Artikal> lista = (List<Artikal>) rez;
        boolean ok = lista != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? new GenericEntity<List<Artikal>>(lista){} : "Greska!").build();
    }
}