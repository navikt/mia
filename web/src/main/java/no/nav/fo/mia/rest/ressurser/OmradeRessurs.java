package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.consumer.endpoints.StillingerEndpoint;
import no.nav.fo.mia.domain.geografi.Omrade;
import no.nav.metrics.aspects.Timed;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/omrader")
@Produces(APPLICATION_JSON)
@Timed
public class OmradeRessurs {

    @Inject
    StillingerEndpoint stillingerEndpoint;

    @GET
    public List<Omrade> hentFylkerOgKommuner() {
        return stillingerEndpoint.getFylkerOgKommuner();
    }
}
