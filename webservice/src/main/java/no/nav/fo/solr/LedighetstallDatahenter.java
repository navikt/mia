package no.nav.fo.solr;

import no.nav.fo.consumer.fillager.Fillager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

public class LedighetstallDatahenter {
    private static final long HVERT_5_MINUTT = 5 * 60 * 1000;
    private static final long FEM_SEKUNDER = 5 * 1000;
    private static final String ARBEIDSLEDIGHET_FILNAVN = "statistikk_arbeidsledige.csv";
    private static final String LEDIGESTILLINGER_FILNAVN = "statistikk_ledigestillinger.csv";

    private long arbeidsledighetLastModified = 0;
    private long ledigeStillingerLastModified = 0;

    private Logger logger = LoggerFactory.getLogger(LedighetstallDatahenter.class);

    @Inject
    IndekserSolr indekserSolr;

    @Inject
    Fillager fillager;

    @Value("${mia.datadir}")
    private String ledighetstallFolder;

    @Scheduled(initialDelay = FEM_SEKUNDER, fixedRate = HVERT_5_MINUTT)
    public void oppdaterLedighetstallOmFilErEndret() {
        logger.info("Sjekker om ny statistikk har blitt lastet opp.");
        oppdaterArbeidsledighetHvisFilErEndret();
        oppdaterLedigestillingerHvisFilErEndret();
    }

    private void oppdaterArbeidsledighetHvisFilErEndret() {
        String arbedsledighetFullPath = String.format("%s/%s", ledighetstallFolder, ARBEIDSLEDIGHET_FILNAVN);
        long sistOppdatert = fillager.getLastModified(arbedsledighetFullPath);


        if(arbeidsledighetLastModified == 0) {
            arbeidsledighetLastModified = sistOppdatert;
        }

        if(sistOppdatert > arbeidsledighetLastModified) {
            logger.info("Fant ny versjon av arbeidsledighet, oppdaterer SOLR...");
            arbeidsledighetLastModified = sistOppdatert;

            try {
                InputStream fileInputStream = fillager.getFileAsStream(arbedsledighetFullPath);
                indekserSolr.lesArbeidsledighetCSVOgSkrivTilSolr(fileInputStream);
            } catch(IOException e) {
                logger.error("Kunne ikke lese filen for arbeidsledighet fra disk.", e);
            }
        }
    }

    private void oppdaterLedigestillingerHvisFilErEndret() {
        String ledigestillingerFullPath = String.format("%s/%s", ledighetstallFolder, LEDIGESTILLINGER_FILNAVN);
        long sistOppdatert = fillager.getLastModified(ledigestillingerFullPath);
        if(ledigeStillingerLastModified == 0) {
            ledigeStillingerLastModified = sistOppdatert;
        }

        if(sistOppdatert > ledigeStillingerLastModified) {
            logger.info("Fant ny versjon av ledigeStillinger, oppdaterer SOLR...");
            ledigeStillingerLastModified = sistOppdatert;

            try {
                InputStream fileInputStream = fillager.getFileAsStream(ledigestillingerFullPath);
                indekserSolr.lesLedigeStillingerCSVOgSkrivTilSolr(fileInputStream);
            } catch(IOException e) {
                logger.error("Kunne ikke lese filen for ledigestillinger fra disk.", e);
            }
        }
    }
}
