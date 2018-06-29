package no.nav.fo.mia.config

import org.elasticsearch.client.RestHighLevelClient
import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
open class ElasticHealth @Inject
constructor(
        val client: RestHighLevelClient
) : AbstractHealthIndicator() {
    override fun doHealthCheck(builder: Health.Builder) {
        val response= client.lowLevelClient.performRequest("get", "/_cluster/health")
        val statusCode = response.statusLine.statusCode
        val info = client.info()
        if(statusCode in 200..299 && info.isAvailable) {
            builder.up()
        }
        else {
            builder.down()
        }
        builder.withDetail("clusterName", info.clusterName.value())
        builder.withDetail("clusterUuid", info.clusterUuid)
        builder.withDetail("isAvailable", info.isAvailable)
        builder.withDetail("version", info.version)
    }
}
