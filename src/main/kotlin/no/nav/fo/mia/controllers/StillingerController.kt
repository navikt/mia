package no.nav.fo.mia.controllers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.consumers.StilliingerConsumer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.ws.rs.BeanParam

@RestController
@RequestMapping("/rest/stillinger")
class StillingerController2 @Inject
constructor(
        val stillingerService: StilliingerConsumer
) {

    @GetMapping("/antallstillinger")
    fun antallStillinger(@BeanParam filtervalg: Filtervalg): Int =
            stillingerService.getAntallStillinger(komunerFra(filtervalg), underkategorierFra(filtervalg))
}
