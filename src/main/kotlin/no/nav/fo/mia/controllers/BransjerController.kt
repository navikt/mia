package no.nav.fo.mia.controllers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.service.StillingerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.ws.rs.BeanParam

@RestController
@RequestMapping("/rest/bransjer")
class BransjerController @Inject
constructor(
        val stillingerService: StillingerService
) {

    @GetMapping("/yrkesomrade")
    fun hentYrkesomrader(@BeanParam filtervalg: Filtervalg) =
            stillingerService.getYrkesomraderMedAntallStillinger(filtervalg)

    @GetMapping("/yrkesgrupper")
    fun hentYrkesgrupper(@RequestParam("yrkesomrade") yrkesomrade: String, @BeanParam filtervalg: Filtervalg) =
            stillingerService.getYrkesgrupperMedAntallStillinger(yrkesomrade, filtervalg)
}