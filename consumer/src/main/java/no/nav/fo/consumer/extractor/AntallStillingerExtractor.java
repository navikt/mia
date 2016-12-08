package no.nav.fo.consumer.extractor;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntallStillingerExtractor {

    private static final Logger logger = LoggerFactory.getLogger(AntallStillingerExtractor.class);

    public static int getAntallStillinger(QueryResponse queryResponse) {
        int totaltAntallStillinger = 0;

        FacetField antallStillingerFacet = queryResponse.getFacetField("ANTALLSTILLINGER");

        if (antallStillingerFacet == null || antallStillingerFacet.getValueCount() == 0) {
            return 0;
        }

        for (FacetField.Count count : antallStillingerFacet.getValues()) {
            long antallAnnonserMedAntallStillinger = count.getCount();
            int antallStillinger = getAntallStillingerForFacet(count);
            totaltAntallStillinger += antallAnnonserMedAntallStillinger * antallStillinger;
        }
        return totaltAntallStillinger;
    }

    private static int getAntallStillingerForFacet(FacetField.Count count) {
        int antallStillinger;
        if (count.getName() == null) {
            antallStillinger = 1;
        } else {
            try {
                antallStillinger = Integer.parseInt(count.getName());
            } catch (NumberFormatException e) {
                logger.warn("Kunne ikke parse antall stillinger fasettverdi '" + count.getName() + "': " + e.getMessage());
                antallStillinger = 0;
            }
        }
        return antallStillinger;
    }
}
