package no.nav.fo.mia.utils;

import no.nav.fo.mia.domain.stillinger.OmradeStilling;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SensureringUtilsTest {
    @Test
    public void sensurerStatistikkdataSkalReturnereOriginaldataOmIngenUnder4() throws Exception {
        Map<String, Integer> statistikk = new HashMap<>();
        statistikk.put("201601", 12);
        statistikk.put("201602", 15);

        Map<String, Integer> sensurert = SensureringUtils.sensurerStatistikkdata(statistikk);

        assertThat(sensurert.get("201601")).isEqualTo(12);
        assertThat(sensurert.get("201602")).isEqualTo(15);
    }

    @Test
    public void sensurerStatistikkdataSkalSetteTallUnder4TilNull() throws Exception {
        Map<String, Integer> statistikk = new HashMap<>();
        statistikk.put("201601", 12);
        statistikk.put("201602", 3);

        Map<String, Integer> sensurert = SensureringUtils.sensurerStatistikkdata(statistikk);

        assertThat(sensurert.get("201601")).isEqualTo(12);
        assertThat(sensurert.get("201602")).isNull();
    }

    @Test
    public void sensurerOmraderSkalReturnereOriginaldataOmIngenArbeidsledigeUnder4() throws Exception {
        List<OmradeStilling> omrader = new ArrayList<>();
        omrader.add(new OmradeStilling("1", 10, 10));

        List<OmradeStilling> sensurert = SensureringUtils.sensurerOmrader(omrader);

        assertThat(sensurert.get(0).getAntallLedige()).isEqualTo(10);
    }

    @Test
    public void sensurerOmraderSkalSensurererArbeidsledigeUnder4() throws Exception {
        List<OmradeStilling> omrader = new ArrayList<>();
        omrader.add(new OmradeStilling("1", 3, 10));

        List<OmradeStilling> sensurert = SensureringUtils.sensurerOmrader(omrader);

        assertThat(sensurert.get(0).getAntallLedige()).isNull();
    }
}