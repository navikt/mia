package no.nav.fo.solr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import java.io.File;

public class LedighetstallDatahenter {
    private static final long HVERT_5_MINUTT = 5 * 60 * 1000;
    private static final long TI_SEKUNDER = 10 * 1000;
    private static final String ARBEIDSLEDIGHET_FILNAVN = "statistikk_arbeidsledige.csv";
    private static final String LEDIGESTILLINGER_FILNAVN = "statistikk_ledigestillinger.csv";

    private long arbeidsledighetLastModified = 0;
    private long ledigeStillingerLastModified = 0;

    private Logger logger = LoggerFactory.getLogger(LedighetstallDatahenter.class);

    @Inject
    IndekserSolr indekserSolr;

    @Value("${mia.datadir}")
    private String ledighetstallFolder;

    @Scheduled(initialDelay = TI_SEKUNDER, fixedRate = HVERT_5_MINUTT)
    public void oppdaterLedighetstallOmFilErEndret() {
        logger.info("Sjekker om ny statistikk har blitt lastet opp.");
        oppdaterArbeidsledighetHvisFilErEndret();
        oppdaterLedigestillingerHvisFilErEndret();
    }

    private void oppdaterArbeidsledighetHvisFilErEndret() {
        String arbedsledighetFullPath = String.format("%s/%s", ledighetstallFolder, ARBEIDSLEDIGHET_FILNAVN);
        long sistOppdatert = getLastModified(arbedsledighetFullPath);
        if(sistOppdatert > arbeidsledighetLastModified) {
            logger.info("Fant ny versjon av arbeidsledighet, oppdaterer SOLR...");
            arbeidsledighetLastModified = sistOppdatert;
            indekserSolr.lesLedigeStillingerCSVOgSkrivTilSolr(ClassLoader.getSystemResourceAsStream(arbedsledighetFullPath));
        }
    }

    private void oppdaterLedigestillingerHvisFilErEndret() {
        String ledigestillingerFullPath = String.format("%s/%s", ledighetstallFolder, LEDIGESTILLINGER_FILNAVN);
        long sistOppdatert = getLastModified(ledigestillingerFullPath);
        if(sistOppdatert > ledigeStillingerLastModified) {
            logger.info("Fant ny versjon av ledigeStillinger, oppdaterer SOLR...");
            ledigeStillingerLastModified = sistOppdatert;
            indekserSolr.lesLedigeStillingerCSVOgSkrivTilSolr(ClassLoader.getSystemResourceAsStream(ledigestillingerFullPath));
        }
    }

    private long getLastModified(String path) {
        File fil = new File(path);
        return fil.lastModified();
    }
}
