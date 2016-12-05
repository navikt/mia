package no.nav.fo.mia.rest.ressurser;

import no.nav.metrics.aspects.Timed;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/ping")
@Produces(APPLICATION_JSON)
@Timed
public class PingRessurs {

    @GET
    public String returnerPong() {
        return "Pong!";
    }
}
