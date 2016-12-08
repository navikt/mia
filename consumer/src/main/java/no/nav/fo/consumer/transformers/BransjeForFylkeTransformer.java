package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.stillinger.Bransje;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BransjeForFylkeTransformer {
    public static List<Bransje> getBransjeForFylke(FacetField yrkesgrupper, FacetField yrkesgrupperid) {
        return IntStream.range(0, yrkesgrupper.getValueCount())
                .mapToObj(i -> getBransje(i, yrkesgrupper, yrkesgrupperid))
                .collect(Collectors.toList());
    }

    private static Bransje getBransje(int index, FacetField yrkesgrupper, FacetField yrkesgrupperid) {
        FacetField.Count yrkesgruppe = yrkesgrupper.getValues().get(index);
        FacetField.Count yrkesgruppeId = yrkesgrupperid.getValues().get(index);
        return new Bransje(yrkesgruppe.getName(), yrkesgruppeId.getName());
    }
}
