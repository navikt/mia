package no.nav.fo.mia.controllers

import no.nav.fo.mia.Bransje
import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.consumers.StilliingerConsumer
import no.nav.fo.mia.util.hovedkategoriTIlunderkategori
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.ws.rs.BadRequestException
import javax.ws.rs.BeanParam

@RestController
@RequestMapping("/rest/bransjer")
class BransjerController2 @Inject
constructor(
        val stillingerService: StilliingerConsumer
) {
    @GetMapping("/")
    fun hentKategorier() = hovedkategoriTIlunderkategori

    @GetMapping("/yrkesomrade")
    fun hentYrkesomrader(filtervalg: Filtervalg): List<Bransje> {
        val esResponse = stillingerService
                .getStillingerForHovedkategorier(komuner = komunerFra(filtervalg))

        return hovedkategoriTIlunderkategori.map {
            Bransje(
                    navn = it.key,
                    id = it.key,
                    antallStillinger = esResponse[it.key] ?: 0
            )
        }

    }

    @GetMapping("/yrkesgruppe")
    fun hentYrkesgrupper(@BeanParam filtervalg: Filtervalg): List<Bransje> { //TODO flytt komune mapping til frontend?

        val yrkesomrade = filtervalg.yrkesomrade ?:
                throw BadRequestException("ingen hovedkategori valgt")

        val underkattegorier = hovedkategoriTIlunderkategori[yrkesomrade]
                ?: throw BadRequestException("ugyldig hovedkategori")

        val esresponse = stillingerService
                .getStillingerForUnderkattegorier(komuner = komunerFra(filtervalg))

        return underkattegorier.map {
            Bransje(
                    navn = it,
                    id = it,
                    antallStillinger = esresponse[it]?: 0,
                    parent = listOf(yrkesomrade)
            )
        }
    }
}
