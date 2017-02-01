package no.nav.fo.mia.utils;

import no.nav.fo.mia.domain.stillinger.OmradeStilling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SensureringUtils {
    public static Map<String, Integer> sensurerStatistikkdata(Map<String, Integer> statistikk) {
        Map<String, Integer> sensurertStatistikkdata = new HashMap<>();
        statistikk.keySet().forEach(key -> sensurertStatistikkdata.put(key, getSensurertAntall(statistikk.get(key))));
        return sensurertStatistikkdata;
    }

    public static List<OmradeStilling> sensurerOmrader(List<OmradeStilling> omrader) {
        return omrader.stream()
                .map(omrade -> {
                    Integer antallLedige = omrade.getId().startsWith("110011") ? omrade.getAntallLedige() : getSensurertAntall(omrade.getAntallLedige());
                    return new OmradeStilling(omrade.getId(), antallLedige, omrade.getAntallStillinger());
                })
                .collect(Collectors.toList());
    }

    private static Integer getSensurertAntall(Integer antall) {
        return antall < 4 ? null : antall;
    }
}
