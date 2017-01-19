package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.consumer.endpoints.StillingerEndpoint;
import no.nav.fo.mia.domain.geografi.Omrade;
import no.nav.fo.mia.domain.stillinger.OmradeStilling;
import no.nav.metrics.aspects.Timed;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.*;

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

    @GET
    @Path("/kommunedata")
    public List<OmradeStilling> hentKommunedata(@BeanParam FiltreringParams filtrering) {
        return stillingerEndpoint.getAntallStillingerForFiltrering(filtrering.yrkesomradeid, filtrering.yrkesgrupper, filtrering.fylker, filtrering.kommuner);
    }

    private static class FiltreringParams {
        @QueryParam("yrkesomrade")
        public String yrkesomradeid;

        @QueryParam("yrkesgrupper[]")
        public List<String> yrkesgrupper;

        @QueryParam("fylker[]")
        public List<String> fylker;

        @QueryParam("kommuner[]")
        public List<String> kommuner;
    }
}
