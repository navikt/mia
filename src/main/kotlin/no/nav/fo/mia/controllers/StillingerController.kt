package no.nav.fo.mia.controllers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.Stilling
import no.nav.fo.mia.service.StillingerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.ws.rs.BeanParam

@RestController
@RequestMapping("/rest/stillinger")
class StillingerController @Inject
constructor(
        val stillingerService: StillingerService
) {
    @GetMapping
    fun hentStillingsannonser(@RequestParam("yrkesgrupper") yrkesgrupper: List<String>, filtervalg: Filtervalg): List<Stilling> =
            stillingerService.getStillingsannonser(yrkesgrupper, filtervalg)

    @GetMapping("/antallstillinger")
    fun antallStillinger(@BeanParam filtervalg: Filtervalg): Int =
            stillingerService.getAntallStillingerForValgtOmrade(filtervalg)
}
