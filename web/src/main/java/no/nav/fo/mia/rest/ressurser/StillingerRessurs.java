package no.nav.fo.mia.rest.ressurser;

import no.nav.fo.mia.domain.stillinger.KommuneStillinger;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.Arrays;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/stillinger")
@Produces(APPLICATION_JSON)
public class StillingerRessurs {

    @GET
    @Path("/oversikt")
    public List<KommuneStillinger> hentOversiktForAlleKommuner() {
        return Arrays.asList(
                new KommuneStillinger()
                    .withKommunenummer("0301")
                    .withAntallLedige(82)
                    .withAntallStillinger(31),
                new KommuneStillinger()
                        .withKommunenummer("1001")
                        .withAntallLedige(10)
                        .withAntallStillinger(22),
                new KommuneStillinger()
                        .withKommunenummer("1002")
                        .withAntallLedige(2)
                        .withAntallStillinger(4),
                new KommuneStillinger()
                        .withKommunenummer("1003")
                        .withAntallLedige(8)
                        .withAntallStillinger(2),
                new KommuneStillinger()
                        .withKommunenummer("1004")
                        .withAntallLedige(12)
                        .withAntallStillinger(14),
                new KommuneStillinger()
                        .withKommunenummer("1026")
                        .withAntallLedige(8)
                        .withAntallStillinger(3),
                new KommuneStillinger()
                        .withKommunenummer("1032")
                        .withAntallLedige(6)
                        .withAntallStillinger(2),
                new KommuneStillinger()
                        .withKommunenummer("0701")
                        .withAntallLedige(7)
                        .withAntallStillinger(4),
                new KommuneStillinger()
                        .withKommunenummer("0704")
                        .withAntallLedige(1)
                        .withAntallStillinger(5),
                new KommuneStillinger()
                        .withKommunenummer("0709")
                        .withAntallLedige(5)
                        .withAntallStillinger(1),
                new KommuneStillinger()
                        .withKommunenummer("0720")
                        .withAntallLedige(22)
                        .withAntallStillinger(7),
                new KommuneStillinger()
                        .withKommunenummer("0719")
                        .withAntallLedige(6)
                        .withAntallStillinger(2)
        );
    }
}
