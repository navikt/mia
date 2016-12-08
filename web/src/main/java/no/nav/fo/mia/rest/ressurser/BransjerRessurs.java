package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.consumer.endpoints.StillingerEndpoint;
import no.nav.fo.mia.domain.stillinger.Bransje;
import no.nav.fo.mia.domain.stillinger.Stilling;
import no.nav.metrics.aspects.Timed;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    public List<Bransje> hentYrkesomrader(@QueryParam("fylkesnummer") String fylkesnummer) {
        return stillingerEndpoint.getYrkesomraderForFylke(fylkesnummer);
    }

    @GET
    @Path("/yrkesgruppe")
    public List<Bransje> hentYrkesgrupper(@QueryParam("yrkesomrade") String yrkesomrade) {
        return stillingerEndpoint.getYrkesgrupperForYrkesomrade(yrkesomrade);
    }

    @GET
    @Path("/stillinger")
    public List<Stilling> hentYrkesgrupper(@QueryParam("yrkesgrupper[]") List<String> yrkesgrupper) {
        return stillingerEndpoint.getStillinger(yrkesgrupper);
    }
}
