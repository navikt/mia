package no.nav.fo.mia.controllers

import no.nav.fo.mia.Bransje
import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.consumers.StillingerConsumer
import no.nav.fo.mia.util.hovedkategoriTilUnderkategori
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.ws.rs.BadRequestException
import javax.ws.rs.BeanParam

@RestController
@RequestMapping("/rest/bransjer")
class BransjerController @Inject constructor(val stillingerService: StillingerConsumer) {
    @GetMapping("/")
    fun hentKategorier() = hovedkategoriTilUnderkategori

    @GetMapping("/yrkesomrade")
    fun hentYrkesomrader(filtervalg: Filtervalg): List<Bransje> {
        val esResponse = stillingerService
                .getStillingerPerHovedkategorier(komuner = kommunerFra(filtervalg))

        return hovedkategoriTilUnderkategori.map {
            Bransje(
                    navn = it.key,
                    id = it.key,
                    antallStillinger = esResponse[it.key] ?: 0
            )
        }

    }

    @GetMapping("/yrkesgruppe")
    fun hentYrkesgrupper(@BeanParam filtervalg: Filtervalg): List<Bransje> {

        val yrkesomrade = filtervalg.yrkesomrade ?: throw BadRequestException("ingen hovedkategori valgt")

        val underkattegorier = hovedkategoriTilUnderkategori[yrkesomrade]
                ?: throw BadRequestException("ugyldig hovedkategori")

        val esresponse = stillingerService
                .getStillingerPerUnderkategorier(komuner = kommunerFra(filtervalg))

        return underkattegorier.map {
            Bransje(
                    navn = it,
                    id = it,
                    antallStillinger = esresponse[it] ?: 0,
                    parent = listOf(yrkesomrade)
            )
        }
    }
}
