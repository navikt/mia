package no.nav.fo.mia.rest.ressurser;

import no.nav.metrics.aspects.Timed;
import no.nav.modig.core.exception.ApplicationException;
import no.nav.sbl.tekster.TeksterAPI;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.ResourceBundle;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/tekster")
@Produces(APPLICATION_JSON)
@Timed
public class TeksterRessurs {
    @Inject
    TeksterAPI teksterApi;

    @GET
    public ResourceBundle hentTekster(@QueryParam("lang") String lang) {
        return teksterApi.hentTekster(lang);
    }

}
