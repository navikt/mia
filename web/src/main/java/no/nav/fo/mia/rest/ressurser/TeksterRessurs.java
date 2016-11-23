package no.nav.fo.mia.rest.ressurser;

import no.nav.modig.core.exception.ApplicationException;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/tekster")
@Produces(APPLICATION_JSON)
public class TeksterRessurs {
    private static Properties innebygdeLedetekster = new Properties();

    static {
        lastInnebygdeLedetekster();
    }

    @GET
    public Properties hentTekster() {
        return innebygdeLedetekster;
    }

    private static void lastInnebygdeLedetekster() {
        String path = "/messages/mia_nb.properties";
        try {
            innebygdeLedetekster.load(new InputStreamReader(TeksterRessurs.class.getResourceAsStream(path), "UTF-8"));
        } catch (IOException e) {
            throw new ApplicationException("Klarte ikke hente tekster fra fil: " + path, e);
        }
    }
}
