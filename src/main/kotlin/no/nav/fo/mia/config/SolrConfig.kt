package no.nav.fo.mia.config

import no.nav.fo.mia.util.getRequiredProperty
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

const val stillingerCore = "maincore"
const val suportcore = "supportcore"



@Configuration
@Profile("!mock")
open class SolrConfig {
    @Bean
    open fun stillingSolrClient(): SolrClient =
            HttpSolrClient.Builder()
                    .withBaseSolrUrl(getRequiredProperty("STILLING_SOLR_URL"))
                    .withConnectionTimeout(2_000)
                    .withSocketTimeout(10_000)
                    .build()
}
