package endpoints;

import entities.Grad;
import java.io.Serializable;
import java.util.List;
import jms.JmsService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.annotation.*;

@Path("grad")
@Stateless
public class GradResource {

    @EJB
    JmsService jmsService;

    @XmlRootElement(name = "requestDTO")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GradRequest {
        @XmlElement public String ime;
        public GradRequest() {}
    }
    
    // 2) KREIRAJ GRAD
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response create(GradRequest req) throws Exception {
        if (req == null || req.ime == null)
            return Response.status(400).type(MediaType.APPLICATION_XML).entity("Obavezna polja nedostaju!").build();

        Serializable rez = jmsService.sendAndWait(2, req.ime);
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? rez : "Greska!").build();
    }

     // 15) SVI GRADOVI
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAll() throws Exception {
        Serializable rez = jmsService.sendAndWait(15, null);
        List<Grad> lista = (List<Grad>) rez;
        boolean ok = rez != null;
        return Response.status(ok ? 200 : 400).type(MediaType.APPLICATION_XML).entity(ok ? new GenericEntity<List<Grad>>(lista){} : "Greska!").build();
    }
}