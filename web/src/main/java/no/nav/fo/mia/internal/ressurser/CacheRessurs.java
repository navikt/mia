package no.nav.fo.mia.internal.ressurser;

import no.nav.metrics.aspects.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import org.springframework.cache.CacheManager;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Controller
@Path("/cache")
@Produces(APPLICATION_JSON)
@Timed
public class CacheRessurs {

    private final Logger logger = LoggerFactory.getLogger(CacheRessurs.class);

    @Inject
    CacheManager cacheManager;

    @GET
    @Path("/clear")
    public String indekserMiASolrArbeidsledighet() {
        logger.info("Sletter alle cacher etter manuell trigger!");
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
        return "Alle cacher slettet!";
    }
}
