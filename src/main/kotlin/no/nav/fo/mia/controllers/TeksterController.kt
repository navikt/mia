package no.nav.fo.mia.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rest/tekster")
class TeksterController {
    @GetMapping
    fun hentTekster(): HashMap<String, String> =
            HashMap()
}