package no.nav.fo;

import no.nav.fo.endpoints.HentDataEndpoint;
import no.nav.fo.transformers.CSVTransformer;
import no.nav.sbl.dialogarena.common.jetty.Jetty;

import static no.nav.sbl.dialogarena.common.jetty.Jetty.usingWar;
import static no.nav.sbl.dialogarena.common.jetty.JettyStarterUtils.*;

public class StartJetty {
    private static final int PORT = 8486;

    public static void main(String[] args) throws Exception {
        Jetty jetty = usingWar()
                .at("/mia")
                .loadProperties("/test.properties")
                .overrideWebXml()
                .port(PORT)
                .buildJetty();


        new CSVTransformer().lesArbeidsledighetCSV();
        new CSVTransformer().lesLedigeStillingerCSV();

        HentDataEndpoint.indekserSOLRIndex();

        jetty.startAnd(first(waitFor(gotKeypress())).then(jetty.stop));

    }
}
