package no.nav.fo.mia

import no.nav.fo.mia.util.setupTruststore
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class ApplicationTest

fun main(args: Array<String>) {
    setupTruststore()
    SpringApplication.run(Application::class.java, *args)
}
