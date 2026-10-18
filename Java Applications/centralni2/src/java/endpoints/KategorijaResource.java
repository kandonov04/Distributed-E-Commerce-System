package endpoints;

import entities.Kategorija;
import java.io.Serializable;
import java.util.List;
import jms.JmsService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.annotation.*;

@Path("kategorija")
@Stateless
public class KategorijaResource {

    @EJB
    JmsService jmsService;

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class KategorijaRequest {
        @XmlElement public String ime;
        @XmlElement public Integer idKat;
        public KategorijaRequest() {}
    }

    // 6) KREIRAJ KATEGORIJU
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response create(KategorijaRequest req) throws Exception {
        if (req == null || req.ime == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();

        String podaci = req.ime + "," + (req.idKat != null ? req.idKat : "null");

        Serializable rez = jmsService.sendAndWait(6, podaci);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();
    }

    // 17) SVE KATEGORIJE
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAll() throws Exception {
        Serializable rez = jmsService.sendAndWait(17, null);
        List<Kategorija> lista = (List<Kategorija>) rez;
        boolean ok = lista != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? new GenericEntity<List<Kategorija>>(lista){} : "Greska!").build();
    }
}