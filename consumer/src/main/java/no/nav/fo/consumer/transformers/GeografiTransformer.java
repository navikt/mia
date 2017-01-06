package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.geografi.Omrade;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GeografiTransformer {
    private static final String GENERELT_FOR_FYLKE_PREFIX = "110011";
    public static List<Omrade> transformResponseToFylkerOgKommuner(SolrDocumentList solrDocuments) {
        List<Omrade> alleOmrader = solrDocuments.stream()
                .map(GeografiTransformer::createOmradeFromDocument)
                .filter(omrade -> omrade.getStrukturkode() != null || omrade.getId().startsWith(GENERELT_FOR_FYLKE_PREFIX))
                .filter(omrade -> !omrade.getNavn().contains("Ikke i bruk"))
                .filter(omrade -> !omrade.getNavn().contains("UTGÅTT"))
                .filter(omrade -> !omrade.getNavn().contains("gml"))
                .filter(omrade -> !omrade.getNavn().contains("Gml"))
                .filter(omrade -> !omrade.getNavn().contains("gammel"))
                .collect(Collectors.toList());

        return alleOmrader.stream()
                .filter(GeografiTransformer::erFylke)
                .map(omrade -> omrade.withUnderomrader(
                        alleOmrader.stream()
                                .filter(underomrade -> underomrade.getNivaa().equals("3") && underomrade.hasParent(omrade.getId()))
                                .collect(Collectors.toList())
                )).collect(Collectors.toList());
    }

    private static boolean erFylke(Omrade omrade) {
        return omrade.getNivaa().equals("2");
    }

    private static Omrade createOmradeFromDocument(SolrDocument document) {
        Omrade omrade = new Omrade(
                getFieldValue("NIVAA", document),
                getFieldValue("ID", document),
                getFieldValue("NAVN", document),
                getFieldValue("STRUKTURKODE", document)
        );

        Collection<Object> parents = document.getFieldValues("PARENT");
        if(parents != null) {
            omrade.withParents(parents.stream().map(Object::toString).collect(Collectors.toList()));
        }

        return omrade;
    }

    private static String getFieldValue(String name, SolrDocument document) {
        Object value = document.getFieldValue(name);
        return value != null ? value.toString() : null;
    }
}
