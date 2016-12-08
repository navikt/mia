package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.stillinger.Stilling;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StillingTransformerTest {
    @Test
    public void skalTransformereEnStilling() {
        SolrDocumentList documentList = new SolrDocumentList();
        documentList.add(createStilling("ARBEIDSGIVER", "1"));

        List<Stilling> stillinger = StillingTransformer.getStillinger(documentList);
        assertThat(stillinger.get(0).getArbeidsgivernavn()).isEqualTo("ARBEIDSGIVER");
    }

    @Test
    public void skalTransformereDatoTilGyldigIsoString() {
        SolrDocumentList documentList = new SolrDocumentList();
        SolrDocument document = createStilling("ARBEIDSGIVER", "1");
        document.setField("SOKNADSFRIST", new Date(2016-1900, 0, 1));
        documentList.add(document);

        List<Stilling> stillinger = StillingTransformer.getStillinger(documentList);
        assertThat(stillinger.get(0).getSoknadfrist()).isEqualTo("2016-01-01T00:00:00");
    }

    private SolrDocument createStilling(String arbeidsgivernavn, String id) {
        SolrDocument document = new SolrDocument();
        document.addField("ARBEIDSGIVERNAVN", arbeidsgivernavn);
        document.addField("SOKNADSFRIST", new Date());
        document.addField("ID", id);
        document.addField("YRKGR_LVL_1_ID", new ArrayList<String>());
        document.addField("YRKGR_LVL_2_ID", new ArrayList<String>());
        return document;
    }
}