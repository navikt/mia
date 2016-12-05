package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.consumer.kodeverk.FylkerOgKommunerReader;
import no.nav.fo.mia.domain.kodeverk.FylkeKodeverk;
import no.nav.metrics.aspects.Timed;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/kodeverk")
@Produces(APPLICATION_JSON)
@Timed
public class KodeverkRessurs {
    private static List<FylkeKodeverk> fylker;

    static {
        fylker = FylkerOgKommunerReader.getFylkerOgKommuner();
    }

    @GET
    @Path("/fylker")
    public List<FylkeKodeverk> hentFylkerOgKommuner() {
        return fylker;
    }
}
