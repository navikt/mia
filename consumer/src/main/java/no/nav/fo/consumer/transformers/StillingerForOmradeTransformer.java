package no.nav.fo.consumer.transformers;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

public class StillingerForOmradeTransformer {

    public static int getAntallStillingerFraQuery(QueryResponse queryResponse) {
       int antallStillinger = 0;
       List<FacetField.Count> counts = queryResponse.getFacetField("ANTALLSTILLINGER").getValues();
        for (FacetField.Count count : counts) {
            if (count.getName() == null) {
                antallStillinger += count.getCount();
            } else {
                antallStillinger += Integer.parseInt(count.getName()) * count.getCount();
            }
        }

        return antallStillinger;
    }
}
