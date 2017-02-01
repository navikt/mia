package no.nav.fo.mia.domain;

import javax.ws.rs.QueryParam;
import java.util.List;

public class Filtervalg {
    @QueryParam("yrkesomrade")
    public String yrkesomrade;

    @QueryParam("yrkesgrupper[]")
    public List<String> yrkesgrupper;

    @QueryParam("fylker[]")
    public List<String> fylker;

    @QueryParam("kommuner[]")
    public List<String> kommuner;
}
