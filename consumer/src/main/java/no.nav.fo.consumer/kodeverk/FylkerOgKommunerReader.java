package no.nav.fo.consumer.kodeverk;

import no.nav.fo.mia.domain.kodeverk.FylkeKodeverk;
import no.nav.fo.mia.domain.kodeverk.KommuneKodeverk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class FylkerOgKommunerReader {
    public static List<FylkeKodeverk> getFylkerOgKommuner() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(FylkerOgKommunerReader.class.getResourceAsStream("/kommuner.csv")));

        List<KommuneCsvRecord> records = reader.lines()
                .map(line -> line.split(","))
                .filter(splittedLine -> splittedLine.length == 6)
                .map(splittedLine -> new KommuneCsvRecord(splittedLine[1], splittedLine[2], splittedLine[4], splittedLine[5]))
                .collect(Collectors.toList());

        return records.stream()
                .map(record -> new FylkeKodeverk(record.getFylkenavn(), record.getFylkesnummer()))
                .distinct()
                .map(fylke -> fylke.withKommuner(records.stream()
                        .filter(record -> record.getFylkesnummer().equals(fylke.getFylkesnummer()))
                        .map(record -> new KommuneKodeverk(record.getKommunenavn(), record.getKommunenummer()))
                        .collect(Collectors.toList()))
                ).collect(Collectors.toList());
    }

    private static class KommuneCsvRecord {
        private String fylkenavn;
        private String fylkeid;
        private String kommunenavn;
        private String kommuneid;

        KommuneCsvRecord(String fylkenavn, String fylkeid, String kommunenavn, String kommuneid) {
            this.fylkenavn = fylkenavn;
            this.fylkeid = fylkeid;
            this.kommunenavn = kommunenavn;
            this.kommuneid = kommuneid;
        }

        String getFylkenavn() {
            return fylkenavn;
        }

        void setFylkenavn(String fylkenavn) {
            this.fylkenavn = fylkenavn;
        }

        String getFylkesnummer() {
            return fylkeid;
        }

        void setFylkeid(String fylkeid) {
            this.fylkeid = fylkeid;
        }

        String getKommunenavn() {
            return kommunenavn;
        }

        void setKommunenavn(String kommunenavn) {
            this.kommunenavn = kommunenavn;
        }

        String getKommunenummer() {
            return kommuneid;
        }

        void setKommuneid(String kommuneid) {
            this.kommuneid = kommuneid;
        }
    }
}
