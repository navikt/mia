package no.nav.fo.mia.config

import no.nav.fo.mia.util.getOptionalProperty
import no.nav.fo.mia.util.getRequiredProperty
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestClientBuilder
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.core.env.Environment
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.inject.Inject

@Configuration
@Profile("!mock")
open class EsConfig @Inject constructor(
        private val env: Environment
) {
    @Bean
    open fun elasticClient(): RestHighLevelClient =
            RestHighLevelClient(
                    RestClient
                            .builder(createHttpHost())
                            .setHttpClientConfigCallback(HttpClientConfigCallback())
            )

    private fun createHttpHost() =
            HttpHost(
                    getRequiredProp("mia.elastic.hostname"),
                    getRequiredProp("mia.elastic.port").toInt(),
                    getRequiredProp("mia.elastic.scheme")
            )

    private fun getRequiredProp(propName: String) =
            getOptionalProperty(propName) ?: env.getRequiredProperty(propName)
}

class HttpClientConfigCallback : RestClientBuilder.HttpClientConfigCallback {
    override fun customizeHttpClient(httpAsyncClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder =
            httpAsyncClientBuilder.setDefaultCredentialsProvider(createCredentialsProvider())

    private fun createCredentialsProvider(): BasicCredentialsProvider {
        val credentialsProvider = BasicCredentialsProvider()
        val credentials = UsernamePasswordCredentials(
                getRequiredProperty("MIA_ELASTIC_USER"),
                getRequiredProperty("MIA_ELASTIC_PSW")
        )
        credentialsProvider.setCredentials(AuthScope.ANY, credentials)
        return credentialsProvider
    }
}
