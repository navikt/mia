package no.nav.fo.mia

import org.elasticsearch.client.RestHighLevelClient
import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.inject.Inject

@Profile("!mock")
@Component
open class ElasticHealth @Inject
constructor(
        val client: RestHighLevelClient
) : AbstractHealthIndicator() {
    private val LOGGER = LoggerFactory.getLogger(ElasticHealth::class.java)
    override fun doHealthCheck(builder: Health.Builder) {

        val response= client.lowLevelClient.performRequest("get", "/_cluster/health")
        val statusCode = response.statusLine.statusCode
        val info = client.info()

        if (statusCode in 200..299 && info.isAvailable) {
            builder.up()
        }
        else {
            LOGGER.error("elastic bad {}", statusCode)
            builder.down()
            builder.withDetail("ErrorCode", statusCode)
        }

        builder.withDetail("clusterName", info.clusterName.value())
        builder.withDetail("clusterUuid", info.clusterUuid)
        builder.withDetail("isAvailable", info.isAvailable)
        builder.withDetail("version", info.version)
    }
}
