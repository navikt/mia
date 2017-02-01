package no.nav.fo.consumer.transformers;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;

public class StillingerForOmradeTransformer {

    public static int getAntallStillingerFraQuery(QueryResponse queryResponse) {
        List<FacetField.Count> counts = queryResponse.getFacetField("ANTALLSTILLINGER").getValues();
        return counts.stream()
                .map(StillingerForOmradeTransformer::getAntallForFacet)
                .mapToInt(Long::intValue)
                .sum();
    }

    private static Long getAntallForFacet(FacetField.Count count) {
        return count.getName() == null ? count.getCount() : Integer.parseInt(count.getName()) * count.getCount();
    }
}
