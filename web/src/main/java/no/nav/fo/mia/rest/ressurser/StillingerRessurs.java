package no.nav.fo.mia.rest.ressurser;

import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/stillinger")
@Produces(APPLICATION_JSON)
public class StillingerRessurs {

    @GET
    @Path("/oversikt")
    public String hentOversiktForAlleKommuner() {
        return "oversikt";
    }
}
