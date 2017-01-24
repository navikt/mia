package no.nav.fo.mia.config;

import no.nav.fo.consumer.endpoints.LedighetsEndpoint;
import no.nav.fo.consumer.endpoints.StillingerEndpoint;
import no.nav.fo.consumer.endpoints.SupportEndpoint;
import no.nav.fo.transformers.IndekserSolr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EndpointsConfig {
    @Bean
    public static StillingerEndpoint stillingerEndpoint() {
        return new StillingerEndpoint();
    }

    @Bean
    public static LedighetsEndpoint ledighetsEndpoint() {
        return new LedighetsEndpoint();
    }

    @Bean
    public static SupportEndpoint mappingUtils() {
        return new SupportEndpoint();
    }

    @Bean
    public static IndekserSolr indekserSolr() {
        return new IndekserSolr();
    }
}
