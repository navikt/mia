package no.nav.fo.mia.rest.ressurser;

import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Controller
@Path("/statistikk")
public class StatistikkRessurs {

    private String heleLandet = "{\"gruppering\":null,\"rader\":[{\"id\":\"\",\"type\":\"tittel\",\n" +
            "\"kolonner\":[\n" +
            "{\"colSpan\":1,\"data\":\"201407\"},\n" +
            "{\"colSpan\":1,\"data\":\"201408\"},\n" +
            "{\"colSpan\":1,\"data\":\"201409\"},\n" +
            "{\"colSpan\":1,\"data\":\"201410\"},\n" +
            "{\"colSpan\":1,\"data\":\"201411\"},\n" +
            "{\"colSpan\":1,\"data\":\"201412\"},\n" +
            "{\"colSpan\":1,\"data\":\"201501\"},\n" +
            "{\"colSpan\":1,\"data\":\"201502\"},\n" +
            "{\"colSpan\":1,\"data\":\"201503\"},\n" +
            "{\"colSpan\":1,\"data\":\"201504\"},\n" +
            "{\"colSpan\":1,\"data\":\"201505\"},\n" +
            "{\"colSpan\":1,\"data\":\"201506\"}],\n" +
            "\"underRader\":[]},\n" +
            "{\"id\":\"\",\"type\":\"detalj\",\n" +
            "\"kolonner\":[\n" +
            "{\"colSpan\":1,\"data\":\"48.83\"},\n" +
            "{\"colSpan\":1,\"data\":\"27.01\"},\n" +
            "{\"colSpan\":1,\"data\":\"36.19\"},\n" +
            "{\"colSpan\":1,\"data\":\"16.31\"},\n" +
            "{\"colSpan\":1,\"data\":\"2.99\"},\n" +
            "{\"colSpan\":1,\"data\":\"24.56\"},\n" +
            "{\"colSpan\":1,\"data\":\"19.79\"},\n" +
            "{\"colSpan\":1,\"data\":\"40.00\"},\n" +
            "{\"colSpan\":1,\"data\":\"19.04\"},\n" +
            "{\"colSpan\":1,\"data\":\"10.40\"},\n" +
            "{\"colSpan\":1,\"data\":\"42.29\"},\n" +
            "{\"colSpan\":1,\"data\":\"37.42\"}],\n" +
            "\"underRader\":[]}\n" +
            "]}\n";

    @GET
    @Path("/ledighet")
    public String hentLedighetStatistikk(@QueryParam("fylker[]") List<String> fylker){
        if(fylker.size() == 0) {
            return heleLandet;
        }
        return  "{\"gruppering\":null,\"rader\":[{\"id\":\"\",\"type\":\"tittel\",\"kolonner\":[{\"colSpan\":1,\"data\":\"201407\"},{\"colSpan\":1,\"data\":\"201408\"},{\"colSpan\":1,\"data\":\"201409\"},{\"colSpan\":1,\"data\":\"201410\"},{\"colSpan\":1,\"data\":\"201411\"},{\"colSpan\":1,\"data\":\"201412\"},{\"colSpan\":1,\"data\":\"201501\"},{\"colSpan\":1,\"data\":\"201502\"},{\"colSpan\":1,\"data\":\"201503\"},{\"colSpan\":1,\"data\":\"201504\"},{\"colSpan\":1,\"data\":\"201505\"},{\"colSpan\":1,\"data\":\"201506\"}],\"underRader\":[]},{\"id\":\"\",\"type\":\"detalj\",\"kolonner\":[{\"colSpan\":1,\"data\":\"48.83\"},{\"colSpan\":1,\"data\":\"27.01\"},{\"colSpan\":1,\"data\":\"36.19\"},{\"colSpan\":1,\"data\":\"16.31\"},{\"colSpan\":1,\"data\":\"2.99\"},{\"colSpan\":1,\"data\":\"24.56\"},{\"colSpan\":1,\"data\":\"19.79\"},{\"colSpan\":1,\"data\":\"40.00\"},{\"colSpan\":1,\"data\":\"19.04\"},{\"colSpan\":1,\"data\":\"10.40\"},{\"colSpan\":1,\"data\":\"42.29\"},{\"colSpan\":1,\"data\":\"37.42\"}],\"underRader\":[]},{\"id\":\"\",\"type\":\"detalj\",\"kolonner\":[{\"colSpan\":1,\"data\":\"8.95\"},{\"colSpan\":1,\"data\":\"35.51\"},{\"colSpan\":1,\"data\":\"33.60\"},{\"colSpan\":1,\"data\":\"21.97\"},{\"colSpan\":1,\"data\":\"41.46\"},{\"colSpan\":1,\"data\":\"33.33\"},{\"colSpan\":1,\"data\":\"17.10\"},{\"colSpan\":1,\"data\":\"12.51\"},{\"colSpan\":1,\"data\":\"35.25\"},{\"colSpan\":1,\"data\":\"2.64\"},{\"colSpan\":1,\"data\":\"18.87\"},{\"colSpan\":1,\"data\":\"7.51\"}],\"underRader\":[]}]}\n";
    }
}
