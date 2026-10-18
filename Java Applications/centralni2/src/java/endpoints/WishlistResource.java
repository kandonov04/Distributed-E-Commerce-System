package endpoints;

import entities.UListiZelja;
import java.io.Serializable;
import jms.JmsService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.annotation.*;
import java.util.List;


@Path("listazelja")
@Stateless
public class WishlistResource {

    @EJB
    JmsService jmsService;

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ListaZeljaArtikalRequest {
        @XmlElement public Integer idKor;
        @XmlElement public Integer idArt;
        public ListaZeljaArtikalRequest() {}
    }

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GetListaZeljaRequest {
        @XmlElement public Integer idKor;
        public GetListaZeljaRequest() {}
    }

    // 12) DODAJ U LISTU ZELJA
    @POST
    @Path("dodaj")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response dodaj(ListaZeljaArtikalRequest req) throws Exception {
        if (req == null || req.idKor == null || req.idArt == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();

        String podaci = req.idKor + "," + req.idArt;

        Serializable rez = jmsService.sendAndWait(12, podaci);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();
    }

    // 13) OBRISI IZ LISTE ZELJA
    @POST
    @Path("obrisi")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response obrisi(ListaZeljaArtikalRequest req) throws Exception {
        if (req == null || req.idKor == null || req.idArt == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();

        String podaci = req.idKor + "," + req.idArt;

        Serializable rez = jmsService.sendAndWait(13, podaci);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();
    }

    // 20) GET LISTA ZELJA
    @POST
    @Path("get")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response getListaZelja(GetListaZeljaRequest req) throws Exception {
        if (req == null || req.idKor == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();
        
        Serializable rez = jmsService.sendAndWait(20, String.valueOf(req.idKor));
        List<UListiZelja> lista = (List<UListiZelja>) rez;
        boolean ok = lista != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? new GenericEntity<List<UListiZelja>>(lista){} : "Greska!").build();
    }
}