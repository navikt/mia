package no.nav.fo.mia.config;

import no.nav.fo.consumer.fillager.Fillager;
import no.nav.fo.solr.LedighetstallDatahenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class LedighetstallConfig {
    @Bean
    public LedighetstallDatahenter ledighetstallDatahenter() {
        return new LedighetstallDatahenter();
    }

    @Bean
    public Fillager fillager() {
        return new Fillager();
    }
}
