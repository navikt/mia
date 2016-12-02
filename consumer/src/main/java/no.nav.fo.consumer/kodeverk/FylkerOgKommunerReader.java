package no.nav.fo.consumer.kodeverk;

import no.nav.fo.mia.domain.kodeverk.FylkeKodeverk;
import no.nav.fo.mia.domain.kodeverk.KommuneKodeverk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FylkerOgKommunerReader {
    public static List<FylkeKodeverk> getFylkerOgKommuner() {
        List<KommuneCsvRecord> kommuneCsvRecords = getKommuneCsvRecords();
        List<KommuneMappingCsvRecord> kommuneMappingCsvRecords = getKommuneMappingCsvRecords();

        return kommuneCsvRecords.stream()
                .map(record -> new FylkeKodeverk(record.getFylkenavn(), record.getFylkesnummer()))
                .distinct()
                .map(fylke -> fylke.withKommuner(getKommunerForFylke(fylke, kommuneCsvRecords, kommuneMappingCsvRecords)))
                .collect(Collectors.toList());
    }

    private static List<KommuneKodeverk> getKommunerForFylke(FylkeKodeverk fylke, List<KommuneCsvRecord> kommuneCsvRecords, List<KommuneMappingCsvRecord> kommuneMappingCsvRecords) {
        return kommuneCsvRecords.stream()
                .filter(record -> record.getFylkesnummer().equals(fylke.getFylkesnummer()))
                .map(record -> new KommuneKodeverk(record.getKommunenavn(), record.getKommunenummer(), getKommuneSolrId(record.getKommunenummer(), kommuneMappingCsvRecords)))
                .filter(kommune -> kommune.getStillingsSolrId() != null)
                .collect(Collectors.toList());
    }

    private static String getKommuneSolrId(String kommunenummer, List<KommuneMappingCsvRecord> kommuneMappingCsvRecords) {
        Optional<KommuneMappingCsvRecord> kommunerecord = kommuneMappingCsvRecords.stream()
                .filter(kommune -> kommune.getKommunenummer().equalsIgnoreCase(kommunenummer))
                .findFirst();
        return kommunerecord.isPresent() ? kommunerecord.get().getSolrid() : null;
    }

    private static List<KommuneCsvRecord> getKommuneCsvRecords() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(FylkerOgKommunerReader.class.getResourceAsStream("/kommuner.csv")));
        return reader.lines()
                .filter(KommuneCsvRecord::isValidCsvLine)
                .map(KommuneCsvRecord::new)
                .collect(Collectors.toList());
    }

    private static List<KommuneMappingCsvRecord> getKommuneMappingCsvRecords() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(FylkerOgKommunerReader.class.getResourceAsStream("/geografi_land_datadump.csv")));
        return reader.lines()
                .filter(KommuneMappingCsvRecord::isValidCsvLine)
                .map(KommuneMappingCsvRecord::new)
                .collect(Collectors.toList());
    }
}
