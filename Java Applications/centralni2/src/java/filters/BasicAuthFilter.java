package filters;

import entities.Korisnik;
import entities.Uloga;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;
import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import jms.JmsService;

@Provider
public class BasicAuthFilter implements ContainerRequestFilter {
    
    @EJB
    JmsService jmsService;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> authHeaderValues = requestContext.getHeaders().get("Authorization");
        if (authHeaderValues != null && authHeaderValues.size() > 0) {
            String authHeaderValue = authHeaderValues.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(
                    authHeaderValue.replaceFirst("Basic ", "")), StandardCharsets.UTF_8);
            
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();
            String password = stringTokenizer.nextToken();
            
            // 1) Proveri kredencijale preko JMS op 1
            Korisnik korisnik = (Korisnik) jmsService.sendAndWait(1, username + "," + password);
            System.out.println("KORISNIK: " + korisnik);
            if (korisnik == null) {
                Response response = Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Korisnicko ime ili sifra nije ispravno.").build();
                requestContext.abortWith(response);
                return;
            }
            
            // 2) Dohvati uloge preko JMS op 29
            List<Uloga> uloge = (List<Uloga>) jmsService.sendAndWait(29, username);
            System.out.println("ULOGE: " + uloge);
            boolean isAdmin = uloge.stream().anyMatch(u -> u.getIme().equals("administrator"));
            
            //String method = requestContext.getMethod();
            UriInfo uriInfo = requestContext.getUriInfo();
            List<PathSegment> pathSegments = uriInfo.getPathSegments();
            String endpointName = pathSegments.get(0).getPath();
            String pathSegment1 = null;
            if (pathSegments.size() > 1)
                pathSegment1 = pathSegments.get(1).getPath();
            
            // login moze svako
            if (endpointName.equals("korisnik") && pathSegment1 != null && pathSegment1.equals("login")) return;
            
            if (isAdmin) return;

            if (!isAdmin && !endpointName.equals("grad") && !(endpointName.equals("korisnik"))) return;

            //&& pathSegment1 != null && !pathSegment1.equals("")

            Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Nemate privilegije.").build();
            requestContext.abortWith(response);
            return;
        }

        Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Posaljite kredencijale.").build();
        requestContext.abortWith(response);
    }
}