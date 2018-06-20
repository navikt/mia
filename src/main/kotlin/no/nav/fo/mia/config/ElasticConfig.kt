package no.nav.fo.mia.config

    import no.nav.fo.mia.util.getRequiredProperty
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestClientBuilder
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.inject.Inject

@Configuration
@Profile("!mock")
open class EsConfig @Inject constructor(
        private val props: EsProps
) {
    @Bean
    open fun elasticClient(): RestHighLevelClient =
            RestHighLevelClient(
                    RestClient
                            .builder(HttpHost(props.hostname, props.port.toInt(), props.scheme))
                            .setHttpClientConfigCallback(HttpClientConfigCallback())
            )
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

@Configuration
@ConfigurationProperties(prefix = "mia.elastic")
open class EsProps {
    lateinit var hostname: String
    lateinit var port: String
    lateinit var scheme: String
}
