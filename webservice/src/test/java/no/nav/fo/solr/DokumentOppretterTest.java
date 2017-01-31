package no.nav.fo.solr;

import no.nav.fo.consumer.service.SupportMappingService;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DokumentOppretterTest {

    @Mock
    SupportMappingService supportMappingService;

    @InjectMocks
    DokumentOppretter dokumentOppretter = new DokumentOppretter();

    @Before
    public void before() {
        when(supportMappingService.getStrukturkodeTilYrkgrLvl2Mapping()).thenReturn(createStrukturkodeTilYrkgrLvl2Mapping());
        when(supportMappingService.getYrkgrLvl2TilYrkgrLvl1Mapping()).thenReturn(createyrkgrLvl2TilYrkgrLvl1Mapping());
    }

    @Test
    public void skalLage2LikeDokumenter() {
        String[] header = new String[]{"PERIODE", "FYLKESNR", "KOMMUNENR", "YRKESKODE", "LEDIGE_STILLINGER", "YRKGR_LVL_1_ID", "YRKGR_LVL_2_ID"};
        String[] csvFelter = new String[]{"201501", "01", "0101", "01", "13", "4321", "yrkgrlvl2-01"};

        Collection<SolrInputDocument> dokumenter = dokumentOppretter.createDocuments(header, csvFelter, 2);

        assertThat(dokumenter.size()).isEqualTo(2);
        Object[] objects = dokumenter.toArray();
        assertThat(objects[0].toString()).isEqualTo(objects[1].toString());
    }

    private Map<String, List<String>> createStrukturkodeTilYrkgrLvl2Mapping() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> yrkgrLvl2 = new ArrayList<>();
        yrkgrLvl2.add("yrkgrlvl2-01");
        yrkgrLvl2.add("yrkgrlvl2-02");

        String strukturkode = "1234";

        map.put(strukturkode, yrkgrLvl2);
        return map;
    }

    private Map<String, List<String>> createyrkgrLvl2TilYrkgrLvl1Mapping() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> yrkgrLvl2 = new ArrayList<>();
        yrkgrLvl2.add("yrkgrlvl2-01");
        yrkgrLvl2.add("yrkgrlvl2-02");

        String lvl1 = "4321";

        map.put(lvl1, yrkgrLvl2);
        return map;
    }
}
