package no.nav.fo.mia.config;

import no.nav.fo.consumer.endpoints.LedighetsEndpoint;
import no.nav.fo.consumer.endpoints.StillingerEndpoint;
import no.nav.fo.consumer.endpoints.SupportEndpoint;
import no.nav.fo.consumer.service.SupportMappingService;
import no.nav.fo.solr.IndekserSolr;
import no.nav.fo.solr.DokumentOppretter;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EndpointsConfig {

    @Bean
    public static HttpSolrClient supportSolrClient() {
        String supportCoreUri = String.format("%s/supportcore", System.getProperty("stilling.solr.url"));
        return new HttpSolrClient.Builder().withBaseSolrUrl(supportCoreUri).build();
    }

    @Bean
    public static StillingerEndpoint stillingerEndpoint() {
        return new StillingerEndpoint();
    }

    @Bean
    public static LedighetsEndpoint ledighetsEndpoint() {
        return new LedighetsEndpoint();
    }

    @Bean
    public static SupportEndpoint supportEndpoint() {
        return new SupportEndpoint();
    }

    @Bean
    public static SupportMappingService supportMappingService() {
        SupportMappingService supportMappingService = new SupportMappingService();
        supportMappingService.createMapping();
        return supportMappingService;
    }

    @Bean
    public static IndekserSolr indekserSolr() {
        return new IndekserSolr();
    }

    @Bean
    public static DokumentOppretter dokumentoppretter() {
         return new DokumentOppretter();
    }
}
