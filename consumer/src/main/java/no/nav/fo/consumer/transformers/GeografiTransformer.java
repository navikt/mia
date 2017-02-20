package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.geografi.Omrade;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GeografiTransformer {
    private static final String GENERELT_FOR_FYLKE_PREFIX = "110011";
    public static List<Omrade> transformResponseToRelevanteOmrader(SolrDocumentList solrDocuments) {
        List<Omrade> alleOmrader = transformerTilOmrader(solrDocuments);
        List<Omrade> alleRelevanteOmrader = lagAlleFylkerOgKommuner(alleOmrader);

        Omrade restenAvVerden = new Omrade("1", "resten av verden", "Resten av verden", "resten av verden");
        Omrade eoseu = new Omrade("1", "EOSEU", "EØS", "EOSEU");

        alleRelevanteOmrader.add(eoseu);
        alleRelevanteOmrader.add(restenAvVerden);

        return alleRelevanteOmrader;
    }

    private static List<Omrade> lagAlleFylkerOgKommuner(List<Omrade> alleOmrader) {
        return alleOmrader.stream()
                    .filter(GeografiTransformer::erFylke)
                    .map(omrade -> omrade.withUnderomrader(
                        alleOmrader.stream()
                            .filter(underomrade -> underomrade.getNivaa().equals("3") && underomrade.hasParent(omrade.getId()))
                            .collect(Collectors.toList())
                    )).collect(Collectors.toList());
    }

    private static List<Omrade> transformerTilOmrader(SolrDocumentList solrDocuments) {
        return solrDocuments.stream()
                    .map(GeografiTransformer::createOmradeFromDocument)
                    .filter(omrade -> omrade.getStrukturkode() != null || omrade.getId().startsWith(GENERELT_FOR_FYLKE_PREFIX) || erEOSEU(omrade) || erRestenAvVerden(omrade))
                    .filter(omrade -> !fjernUtgaatteKommuner(omrade))
                    .collect(Collectors.toList());
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
        return omrade.getParentIds().contains("EOSEU") || harStrukturkodeEU(omrade);
    }

    private static boolean erRestenAvVerden(Omrade omrade) {
        return omrade.getParentIds().contains("resten av verden") && !harStrukturkodeEU(omrade);
    }

    private static boolean harStrukturkodeEU(Omrade omrade) {
        return omrade.getStrukturkode() != null && omrade.getStrukturkode().equalsIgnoreCase("EU");
    }
}
