package no.nav.fo.mia.controllers

import no.nav.fo.mia.util.*
import no.nav.fo.mia.consumers.StilliingerConsumer
import no.nav.fo.mia.util.hovedkategoriTIlunderkategori
import no.nav.fo.mia.util.styrkTilHovedkategori
import no.nav.fo.mia.util.styrkTilUnderkategori
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.lang.NullPointerException
import javax.inject.Inject

@RestController
@RequestMapping("/rest/rat")
class RatController2 @Inject
constructor(
        val stillingerService: StilliingerConsumer
) {
    @GetMapping("/fylke")
    fun rat(
            @RequestParam("kommunennummer") komuneNummer: String,
            @RequestParam("styrkkode") styrkKode: String
    ): r {
        val komuner: List<String>
        val hovedkategori: String
        val underkategori: String
        val underkategorier: List<String>
        val komuneNavn: String
        val fylkesnavn: String
        try {
            val fylkesNummer = komuneNrTilFylkesNr[komuneNummer]!!
            komuner = fylkeTilKommuner[fylkesNummer]!!
            hovedkategori = styrkTilHovedkategori[styrkKode]!!
            underkategori = styrkTilUnderkategori[styrkKode]!!
            underkategorier = hovedkategoriTIlunderkategori[hovedkategori]!!
            komuneNavn = kommuneNrTIlNavn[komuneNummer]!!
            fylkesnavn = fylkesnrTilNavn[fylkesNummer]!!
        } catch (e: NullPointerException) {
            throw IllegalArgumentException("ikke gyldig")
        }

        val under= stillingerService.getAntallStillinger(komuner, listOf(underkategori))
        val hoved = stillingerService.getAntallStillinger(komuner, underkategorier)

        return r(
                kommuneNavn = komuneNavn,
                fylkesnavn = fylkesnavn,
                hovedkategorei = a(hovedkategori, hoved),
                underkategori = a(underkategori, under)
        )
    }
}

data class r(val kommuneNavn: String, val fylkesnavn: String, val hovedkategorei: a, val underkategori: a)
data class a(val kategori: String, val antall: Int)
