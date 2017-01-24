package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.consumer.endpoints.LedighetsEndpoint;
import no.nav.metrics.aspects.Timed;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.*;

import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/ledighet")
@Produces(APPLICATION_JSON)
@Timed
public class LedighetsRessurs {

    @Inject
    LedighetsEndpoint ledighetsEndpoint;

    @GET
    @Path("/statistikk")
    public Map<String, Map<String, Integer>> hentLedighetForSisteTrettenMaaneder(@BeanParam LedighetsRessurs.FiltreringParams filtrering) {
        return ledighetsEndpoint.getLedighetForSisteTrettenMaaneder(filtrering.yrkesgrupper, filtrering.fylker, filtrering.kommuner);
    }

    @GET
    @Path("/allefylker")
    public Map<String, Integer> hentLedighetForAlleFylker() {
        return ledighetsEndpoint.getLedighetForAlleFylker();
    }

//    @GET
//    @Path("/omrader")
//    public Map<String, Integer> hentLedighetForOmrader(@BeanParam FiltreringParams filtreringParams) {
//        return ledighetsEndpoint.getLedighetForOmrader(filtreringParams.yrkesgrupper, filtreringParams.fylker, filtreringParams.kommuner);
//    }

    private static class FiltreringParams {
        @QueryParam("yrkesgrupper[]")
        public List<String> yrkesgrupper;

        @QueryParam("fylker[]")
        public List<String> fylker;

        @QueryParam("kommuner[]")
        public List<String> kommuner;
    }
}
