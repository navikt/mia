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
                    .withKommune(new KommuneKodeverk("Oslo", "0301")),
                new FylkeKodeverk("Vest-agder")
                    .withKommune(new KommuneKodeverk("Kristiansand", "1001"))
                    .withKommune(new KommuneKodeverk("Mandal", "1002"))
                    .withKommune(new KommuneKodeverk("Farsund", "1003"))
                    .withKommune(new KommuneKodeverk("Flekkefjord", "1004"))
                    .withKommune(new KommuneKodeverk("Åseral", "1026"))
                    .withKommune(new KommuneKodeverk("Lyngdal", "1032")),
                new FylkeKodeverk("Vestfold")
                    .withKommune(new KommuneKodeverk("Horten", "0701"))
                    .withKommune(new KommuneKodeverk("Tønsberg", "0704"))
                    .withKommune(new KommuneKodeverk("Larvik", "0709"))
                    .withKommune(new KommuneKodeverk("Stokke", "0720"))
                    .withKommune(new KommuneKodeverk("Andebu", "0719"))
        );
    }
}
