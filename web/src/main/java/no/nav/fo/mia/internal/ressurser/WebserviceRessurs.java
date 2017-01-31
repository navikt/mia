package no.nav.fo.mia.internal.ressurser;

import no.nav.fo.solr.IndekserSolr;
import no.nav.metrics.aspects.Timed;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.InputStream;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/indeksersolr")
@Produces(APPLICATION_JSON)
@Timed
public class WebserviceRessurs {

    @Inject
    IndekserSolr indekserSolr;

    @POST
    @Path("/arbeidsledigecore")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response indekserMiASolrArbeidsledighet(@FormDataParam("file") InputStream file) {
        indekserSolr.lesArbeidsledighetCSV(file);
        return createRedirectResponse("Arbeidsledighet oppdatert!");
    }

    @POST
    @Path("/ledigestillingercore")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response indekserMiASolrLedigeStillinger(@FormDataParam("file") InputStream file) {
        indekserSolr.lesLedigeStillingerCSV(file);
        return createRedirectResponse("Ledige stillinger oppdatert!");
    }

    private Response createRedirectResponse(String entity) {
        return Response.seeOther(UriBuilder.fromPath("/mia/upload.html").build())
                .entity(entity).build();
    }

}
