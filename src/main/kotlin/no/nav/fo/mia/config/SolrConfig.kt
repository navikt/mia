package no.nav.fo.mia.config

import no.nav.fo.mia.util.getRequiredProperty
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.impl.HttpSolrClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("!mock")
open class SolrConfig {
    @Bean
    open fun supportSolrClient(): SolrClient =
            getClientForUri("${getRequiredProperty("STILLING_SOLR_URL")}/supportcore")

    @Bean
    open fun arbeidsledighetSolrClient(): SolrClient =
            getClientForUri(getRequiredProperty("MIASOLR_SOLR_ARBEIDSLEDIGECORE_URL"))

    @Bean
    open fun ledigestillingSolrClient(): SolrClient =
            getClientForUri(getRequiredProperty("MIASOLR_SOLR_LEDIGESTILLINGERCORE_URL"))

    private fun getClientForUri(uri: String): SolrClient =
            HttpSolrClient.Builder().withBaseSolrUrl(uri).build()
}
