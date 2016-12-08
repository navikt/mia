package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.geografi.Omrade;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GeografiTransformerTest {
    private static String FYLKE_NIVAA = "2";
    private static String KOMMUNE_NIVAA = "3";

    @Test
    public void skalTransformereFylkerOgKommuner() {
        SolrDocumentList documentList = new SolrDocumentList();

        documentList.addAll(
                Arrays.asList(
                        createOmrade(FYLKE_NIVAA, "1", "FYLKE1", "NO01", null),
                        createOmrade(KOMMUNE_NIVAA, "2", "KOMMUNE1", "NO01.0595", "1"),
                        createOmrade(KOMMUNE_NIVAA, "3", "KOMMUNE2", "NO01.1234", "1")
                )
        );

        List<Omrade> omrader = GeografiTransformer.transformResponseToFylkerOgKommuner(documentList);

        assertThat(omrader.get(0).getNavn()).isEqualTo("FYLKE1");
        assertThat(omrader.get(0).getUnderomrader().size()).isEqualTo(2);
    }

    private SolrDocument createOmrade(String nivaa, String id, String navn, String strukturkode, String parent) {
        SolrDocument document = new SolrDocument();
        document.addField("NIVAA", nivaa);
        document.addField("ID", id);
        document.addField("NAVN", navn);
        document.addField("STRUKTURKODE", strukturkode);
        document.addField("PARENT", parent);

        return document;
    }
}