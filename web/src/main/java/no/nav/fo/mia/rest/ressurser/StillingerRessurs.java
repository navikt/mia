package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.consumer.endpoints.StillingerEndpoint;
import no.nav.fo.mia.domain.stillinger.OmradeStilling;
import no.nav.metrics.aspects.Timed;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/stillinger")
@Produces(APPLICATION_JSON)
@Timed
public class StillingerRessurs {

    @Inject
    StillingerEndpoint stillingerEndpoint;

    @GET
    @Path("/oversiktAlleKommuner")
    public List<OmradeStilling> hentOversiktForFylkerOgKommuner() {
        return stillingerEndpoint.getAntallStillingerForAlleKommuner();
    }
}
