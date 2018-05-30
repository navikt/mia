package no.nav.fo.mia.controllers

import no.nav.fo.mia.Omrade
import no.nav.fo.mia.service.GeografiService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

@RestController
@RequestMapping("/rest/omrader")
class OmradeController @Inject
constructor(
        val geografiService: GeografiService
) {
    @GetMapping
    fun hentRelevanteOmrader(): List<Omrade> =
            geografiService.hentAlleRelevanteOmrader()
}