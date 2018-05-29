package no.nav.fo.mia.controllers

import no.nav.fo.mia.util.getOptionalProperty
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.HashMap

@RestController
@RequestMapping("/rest/miljovariabler", produces = [(MediaType.APPLICATION_JSON_VALUE)])
class MiljovariabelerController {
    @GetMapping
    fun hentVariabler(): Map<String, String?> {
        val properties = HashMap<String, String?>()
        properties["stillingsok.link.url"] = getOptionalProperty("stillingsok.link.url")
        return properties
    }
}