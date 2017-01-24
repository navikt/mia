package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.transformers.IndekserSolr;
import no.nav.metrics.aspects.Timed;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/indeksersolr")
@Produces(APPLICATION_JSON)
@Timed
public class WebserviceRessurs {

    @Inject
    IndekserSolr indekserSolr;

    @GET
    @Path("/arbeidsledigecore")
    public String indekserMiASolrArbeidsledighet() {
        indekserSolr.lesOgSkrivArbeidsledige();
        return "hei";
    }

    @GET
    @Path("/ledigestillingercore")
    public String indekserMiASolrLedigeStillinger() {
        indekserSolr.lesOgSkrivLedigeStillinger();
        return "hei";
    }

}
