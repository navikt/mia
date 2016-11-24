package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.mia.domain.Fylke;
import no.nav.fo.mia.domain.Kommune;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.Arrays;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/kodeverk")
@Produces(APPLICATION_JSON)
public class KodeverkRessurs {

    @GET
    @Path("/fylker")
    public List<Fylke> hentFylkerOgKommuner() {
        return Arrays.asList(
                new Fylke("Oslo")
                    .withKommune(new Kommune("Oslo")),
                new Fylke("Vest-agder")
                    .withKommune(new Kommune("Kristiansand"))
                    .withKommune(new Kommune("Mandal"))
                    .withKommune(new Kommune("Farsun"))
                    .withKommune(new Kommune("Søgne"))
                    .withKommune(new Kommune("Åseral"))
                    .withKommune(new Kommune("Lyngdal")),
                new Fylke("Vestfold")
                    .withKommune(new Kommune("Horten"))
                    .withKommune(new Kommune("Tønsberg"))
                    .withKommune(new Kommune("Larvik"))
                    .withKommune(new Kommune("Stokke"))
                    .withKommune(new Kommune("Andebu"))
        );
    }
}
