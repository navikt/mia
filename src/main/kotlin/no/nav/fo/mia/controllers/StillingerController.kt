package no.nav.fo.mia.controllers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.service.StillingerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.ws.rs.BeanParam

@RestController
@RequestMapping("/rest/stillinger")
class StillingerController @Inject
constructor(
        val stillingerService: StillingerService
) {
    @GetMapping("/antallstillinger")
    fun antallStillinger(@BeanParam filtervalg: Filtervalg): Int =
            stillingerService.getAntallStillingerForValgtOmrade(filtervalg)
}
