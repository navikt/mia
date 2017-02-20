package no.nav.fo.solr;

import no.nav.fo.consumer.fillager.Fillager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LedighetstallDatahenterTest {
    @Mock
    IndekserSolr indekserSolr;

    @InjectMocks
    LedighetstallDatahenter ledighetstallDatahenter = new LedighetstallDatahenter();

    @Mock
    Fillager fillager;

    @Test
    public void skalIkkeOppdatereSolrForsteGangDenKjorer() {
        when(fillager.getLastModified(any())).thenReturn(10L);
        ledighetstallDatahenter.oppdaterLedighetstallOmFilErEndret();
        verify(indekserSolr, never()).lesLedigeStillingerCSVOgSkrivTilSolr(any());
        verify(indekserSolr, never()).lesArbeidsledighetCSVOgSkrivTilSolr(any());
    }

    @Test
    public void skalIkkeOppdatereSolrandreeGangDenKjorerOmFilIkkeEndret() {
        when(fillager.getLastModified(any())).thenReturn(10L);
        ledighetstallDatahenter.oppdaterLedighetstallOmFilErEndret();
        ledighetstallDatahenter.oppdaterLedighetstallOmFilErEndret();
        verify(indekserSolr, never()).lesLedigeStillingerCSVOgSkrivTilSolr(any());
        verify(indekserSolr, never()).lesArbeidsledighetCSVOgSkrivTilSolr(any());
    }

    @Test
    public void skalOppdatereLedigeStillingerOmFilenErEndret() {
        when(fillager.getLastModified("null/statistikk_ledigestillinger.csv")).thenReturn(10L).thenReturn(20L);
        ledighetstallDatahenter.oppdaterLedighetstallOmFilErEndret();
        ledighetstallDatahenter.oppdaterLedighetstallOmFilErEndret();
        verify(indekserSolr, atLeastOnce()).lesLedigeStillingerCSVOgSkrivTilSolr(any());
    }

    @Test
    public void skalOppdatereArbeidsledighetOmFilenErEndret() {
        when(fillager.getLastModified("null/statistikk_arbeidsledige.csv")).thenReturn(10L).thenReturn(20L);
        ledighetstallDatahenter.oppdaterLedighetstallOmFilErEndret();
        ledighetstallDatahenter.oppdaterLedighetstallOmFilErEndret();
        verify(indekserSolr, atLeastOnce()).lesArbeidsledighetCSVOgSkrivTilSolr(any());
    }
}