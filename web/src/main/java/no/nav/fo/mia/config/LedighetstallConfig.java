package no.nav.fo.mia.config;

import no.nav.fo.solr.DokumentOppretter;
import no.nav.fo.solr.IndekserSolr;
import no.nav.fo.solr.LedighetstallDatahenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LedighetstallConfig {
    @Bean
    public LedighetstallDatahenter ledighetstallDatahenter() {
        return new LedighetstallDatahenter();
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
