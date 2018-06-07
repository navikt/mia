package no.nav.fo.mia

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import no.nav.fo.mia.util.getOptionalProperty
import no.nav.fo.mia.util.setupTruststore
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import javax.inject.Inject

@SpringBootApplication
open class Application

fun main(args: Array<String>) {
    setupTruststore()
    val app = SpringApplication(Application::class.java)
    if (getOptionalProperty("USE_MOCK") == "true") {
        app.setAdditionalProfiles("mock")
    }
    app.run(*args)
}

@Inject
fun configureObjectMapper(mapper: ObjectMapper) {
    mapper.registerModule(ParameterNamesModule())
            .registerModule(Jdk8Module())
            .registerModule(JavaTimeModule())
            .registerModule(KotlinModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
}
