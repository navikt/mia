package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.consumer.endpoints.StillingerEndpoint;
import no.nav.fo.mia.domain.Filtervalg;
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
    public List<Bransje> hentYrkesomrader(@QueryParam("fylkesnummer") String fylkesnummer, @BeanParam Filtervalg filtervalg) {
        return stillingerEndpoint.getYrkesomrader(fylkesnummer, filtervalg);
    }

    @GET
    @Path("/yrkesgruppe")
    public List<Bransje> hentYrkesgrupper(@QueryParam("yrkesomrade") String yrkesomrade, @BeanParam Filtervalg filtervalg) {
        return stillingerEndpoint.getYrkesgrupperForYrkesomrade(yrkesomrade, filtervalg);
    }

    @GET
    @Path("/stillinger")
    public List<Stilling> hentStillinger(@QueryParam("yrkesgrupper[]") List<String> yrkesgrupper, @BeanParam Filtervalg filtervalg) {
        return stillingerEndpoint.getStillinger(yrkesgrupper, filtervalg);
    }
}
