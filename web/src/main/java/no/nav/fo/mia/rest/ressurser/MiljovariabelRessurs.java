package no.nav.fo.mia.rest.ressurser;

import no.nav.metrics.aspects.Timed;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/miljovariabler")
@Produces(APPLICATION_JSON)
@Timed
public class MiljovariabelRessurs {

    @GET
    public Map<String, String> hentVariabler() {
        Map<String, String> properties = new HashMap<>();
        properties.put("stillingsok.link.url", System.getProperty("stillingsok.link.url"));

        return properties;
    }
}
