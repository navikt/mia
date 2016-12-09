package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.consumer.endpoints.StillingerEndpoint;
import no.nav.fo.mia.domain.stillinger.Bransje;
import no.nav.fo.mia.domain.stillinger.Stilling;
import no.nav.metrics.aspects.Timed;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/bransjer")
@Produces(APPLICATION_JSON)
@Timed
public class BransjerRessurs {

    @Inject
    StillingerEndpoint stillingerEndpoint;

    @GET
    @Path("/yrkesomrade")
    public List<Bransje> hentYrkesomrader(@QueryParam("fylkesnummer") String fylkesnummer, @BeanParam FylkerOgKommunerParams fylkerOgKommuner) {
        return stillingerEndpoint.getYrkesomrader(fylkesnummer, fylkerOgKommuner.fylker, fylkerOgKommuner.kommuner);
    }

    @GET
    @Path("/yrkesgruppe")
    public List<Bransje> hentYrkesgrupper(@QueryParam("yrkesomrade") String yrkesomrade, @BeanParam FylkerOgKommunerParams fylkerOgKommuner) {
        return stillingerEndpoint.getYrkesgrupperForYrkesomrade(yrkesomrade, fylkerOgKommuner.fylker, fylkerOgKommuner.kommuner);
    }

    @GET
    @Path("/stillinger")
    public List<Stilling> hentStillinger(@QueryParam("yrkesgrupper[]") List<String> yrkesgrupper, @BeanParam FylkerOgKommunerParams fylkerOgKommuner) {
        return stillingerEndpoint.getStillinger(yrkesgrupper, fylkerOgKommuner.fylker, fylkerOgKommuner.kommuner);
    }

    private static class FylkerOgKommunerParams {
        @QueryParam("fylker[]")
        public List<String> fylker;

        @QueryParam("kommuner[]")
        public List<String> kommuner;
    }
}
