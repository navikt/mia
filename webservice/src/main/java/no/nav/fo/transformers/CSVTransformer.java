package no.nav.fo.transformers;

import no.nav.fo.consumer.endpoints.SupportEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.*;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class CSVTransformer {
    private Logger logger = LoggerFactory.getLogger(CSVTransformer.class);
    private Map<String, List<String>> strukturkodeTilYrkgrLvl2Mapping;

    @Inject
    SupportEndpoint supportEndpointUtils;

    public CSVTransformer() {
        strukturkodeTilYrkgrLvl2Mapping = supportEndpointUtils.getStrukturkodeTilYrkgrLvl2Mapping();
    }

    public void lesArbeidsledighetCSV() {
        InputStream inputStreamArbeidsledige = ClassLoader.getSystemClassLoader().getResourceAsStream("statistikk_arbeidsledige.csv");

        String dataDirNavn = System.getProperty("app.solr.data.dir");

        String arbeidsledighetFilnavn = "arbeidsledighet_solr.csv";
        File arbeidsledighetFil = new File(dataDirNavn, arbeidsledighetFilnavn);

        String header = "\"PERIODE\",\"FYLKESNR\",\"KOMMUNENR\",\"YRKESKODE\",\"ARBEIDSLEDIGE\",\"YRKGR_LVL_2_ID\"";

        lesOgSkrivCSV(inputStreamArbeidsledige, arbeidsledighetFil, header);
    }

    public void lesLedigeStillingerCSV() {
        InputStream inputStreamLedigeStillinger = ClassLoader.getSystemClassLoader().getResourceAsStream("statistikk_ledigestillinger.csv");

        String dataDirNavn = System.getProperty("app.solr.data.dir");
        String ledigestillingerFilnavn = "ledigestillinger_solr.csv";
        File ledigeStillingerFil = new File(dataDirNavn, ledigestillingerFilnavn);

        String header = "\"PERIODE\",\"FYLKESNR\",\"KOMMUNENR\",\"YRKESKODE\",\"LEDIGE_STILLINGER\", \"YRKGR_LVL_2_ID\"";

        lesOgSkrivCSV(inputStreamLedigeStillinger, ledigeStillingerFil, header);
    }

    private BufferedWriter skrivLineToFile(BufferedWriter bufferedWriter, String originalLinje, String[] felter) throws IOException {
        try {
            int antallArbeidsledige = parseInt(felter[4]);
            String strukturkode = felter[3];

            List<String> yrkgrLvl2ider = strukturkodeTilYrkgrLvl2Mapping.get(strukturkode);

            for (String yrkgrLvl2id : yrkgrLvl2ider) {
                for (int i = 0; i < antallArbeidsledige; i++) {
                    bufferedWriter.write(originalLinje);
                    bufferedWriter.write("," + yrkgrLvl2id);
                    bufferedWriter.write("\n");
                }
            }
        } catch (NumberFormatException e) {
            logger.error("Kunne ikke formatere tall ", e.getMessage());
        }
        return bufferedWriter;
    }

    private boolean lesOgSkrivCSV(InputStream inputstream, File outputfile, String header) {

        BufferedReader bufferedReader = null;
        String line = "";
        String cvsSplitBy = ",";
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputstream));

            fileWriter = new FileWriter(outputfile);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedReader.readLine();
            bufferedWriter.write(header + "\n");

            while ((line = bufferedReader.readLine()) != null) {
                String formatertLine = line.replace("\"", "");
                String[] csvFelter = formatertLine.split(cvsSplitBy);

                bufferedWriter = skrivLineToFile(bufferedWriter, line, csvFelter);
            }
            return true;
        } catch (IOException e) {
            logger.error("Feil ved lesing/skriving av fil " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                logger.error("Feil ved lukking av fil ", e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }
}
