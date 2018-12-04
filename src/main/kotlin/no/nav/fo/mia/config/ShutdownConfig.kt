package no.nav.fo.mia.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import javax.annotation.PreDestroy


@Configuration
open class ShutdownConfig {
    private val log = LoggerFactory.getLogger(ShutdownConfig::class.java)

    @PreDestroy
    open fun destroy() {
        log.info("venter 5 sek med shudown pga kybernets lastbalangsering")
        Thread.sleep(5 * 1000)
        log.info("shuting down")
    }
}
