package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.mia.domain.kodeverk.FylkeKodeverk;
import no.nav.fo.mia.domain.kodeverk.KommuneKodeverk;
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
    public List<FylkeKodeverk> hentFylkerOgKommuner() {
        return Arrays.asList(
                new FylkeKodeverk("Oslo")
                    .withKommune(new KommuneKodeverk("Oslo")),
                new FylkeKodeverk("Vest-agder")
                    .withKommune(new KommuneKodeverk("Kristiansand"))
                    .withKommune(new KommuneKodeverk("Mandal"))
                    .withKommune(new KommuneKodeverk("Farsun"))
                    .withKommune(new KommuneKodeverk("Søgne"))
                    .withKommune(new KommuneKodeverk("Åseral"))
                    .withKommune(new KommuneKodeverk("Lyngdal")),
                new FylkeKodeverk("Vestfold")
                    .withKommune(new KommuneKodeverk("Horten"))
                    .withKommune(new KommuneKodeverk("Tønsberg"))
                    .withKommune(new KommuneKodeverk("Larvik"))
                    .withKommune(new KommuneKodeverk("Stokke"))
                    .withKommune(new KommuneKodeverk("Andebu"))
        );
    }
}
