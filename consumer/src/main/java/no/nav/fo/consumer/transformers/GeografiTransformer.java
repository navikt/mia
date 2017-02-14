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
                .filter(omrade -> omrade.getStrukturkode() != null || omrade.getId().startsWith(GENERELT_FOR_FYLKE_PREFIX) || erEOSEU(omrade) || erRestenAvVerden(omrade))
                .filter(omrade -> !fjernUtgaatteKommuner(omrade))
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

    private static boolean fjernUtgaatteKommuner(Omrade omrade) {
        return omrade.getNavn().contains("Ikke i bruk") ||
                omrade.getNavn().contains("UTGÅTT") ||
                omrade.getNavn().contains("gml") ||
                omrade.getNavn().contains("Gml") ||
                omrade.getNavn().contains("gammel");
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

    private static boolean erEOSEU(Omrade omrade) {
        return omrade != null && (omrade.getParentIds().contains("EOSEU") || omrade.getStrukturkode().equalsIgnoreCase("EU"));
    }

    private static boolean erRestenAvVerden(Omrade omrade) {
        return omrade != null && (omrade.getParentIds().contains("resten av verden") && !omrade.getStrukturkode().equalsIgnoreCase("EU"));
    }
}
