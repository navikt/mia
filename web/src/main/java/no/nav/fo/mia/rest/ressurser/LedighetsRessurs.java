package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.consumer.endpoints.LedighetsEndpoint;
import no.nav.fo.mia.domain.Filtervalg;
import no.nav.fo.mia.utils.SensureringUtils;
import no.nav.metrics.aspects.Timed;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.*;

import java.util.HashMap;
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
    public Map<String, Map<String, Integer>> hentLedighetForSisteTrettenMaaneder(@BeanParam Filtervalg filtervalg) {
        Map<String, Map<String, Integer>> statistikk = new HashMap<>();
        statistikk.put("arbeidsledighet", SensureringUtils.sensurerStatistikkdata(ledighetsEndpoint.getArbeidsledighetForSisteTrettenMaaneder(filtervalg)));
        statistikk.put("ledigestillinger", ledighetsEndpoint.getLedigestillingerForSisteTrettenMaaneder(filtervalg));
        return statistikk;
    }

    @GET
    @Path("/allefylker")
    public Map<String, Integer> hentLedighetForAlleFylker() {
        return ledighetsEndpoint.getLedighetForAlleFylker();
    }
}
