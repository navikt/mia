package no.nav.fo.mia.rest.ressurser;

import no.nav.metrics.aspects.Timed;
import no.nav.sbl.tekster.TeksterAPI;
import no.nav.sbl.tekster.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.util.Properties;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/tekster")
@Produces(APPLICATION_JSON)
@Timed
public class TeksterRessurs {
    private final Logger logger = LoggerFactory.getLogger(TeksterRessurs.class);

    @Inject
    TeksterAPI teksterApi;

    @GET
    public Properties hentTekster(@QueryParam("lang") String lang) {
        logger.info("Henter tekster fra disk");
        return Utils.convertResourceBundleToProperties(teksterApi.hentTekster(lang));
    }

}
