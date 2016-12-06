package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.stillinger.BransjeLvl1;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BransjeLvl1ForFylkeTransformer {
    public static List<BransjeLvl1> getBransjeLvlForFylke(FacetField yrkesgrupper, FacetField yrkesgrupperid) {
        return IntStream.range(0, yrkesgrupper.getValueCount())
                .mapToObj(i -> getBransje(i, yrkesgrupper, yrkesgrupperid))
                .collect(Collectors.toList());
    }

    private static BransjeLvl1 getBransje(int index, FacetField yrkesgrupper, FacetField yrkesgrupperid) {
        FacetField.Count yrkesgruppe = yrkesgrupper.getValues().get(index);
        FacetField.Count yrkesgruppeId = yrkesgrupperid.getValues().get(index);
        return new BransjeLvl1(yrkesgruppe.getName(), yrkesgruppeId.getName(), (int)yrkesgruppe.getCount());
    }
}
