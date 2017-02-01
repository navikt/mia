package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.consumer.endpoints.SupportEndpoint;
import no.nav.fo.consumer.endpoints.StillingerEndpoint;
import no.nav.fo.mia.domain.Filtervalg;
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

    @Inject
    SupportEndpoint supportEndpoint;

    @GET
    public List<Omrade> hentFylkerOgKommuner() {
        return supportEndpoint.getFylkerOgKommuner();
    }

    @GET
    @Path("/kommunedata")
    public List<OmradeStilling> hentKommunedata(@BeanParam Filtervalg filtrering) {
        return stillingerEndpoint.getLedighetstallForOmrader(filtrering.yrkesomrade, filtrering.yrkesgrupper, filtrering.fylker, filtrering.kommuner);
    }
}
