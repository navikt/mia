package no.nav.fo.mia.service

import no.nav.fo.mia.Omrade
import no.nav.fo.mia.consumers.GeografiConsumer
import no.nav.fo.mia.consumers.OmradeDTO
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class GeografiService @Inject
constructor(
        val geografiConsumer: GeografiConsumer
) {
    fun hentAlleRelevanteOmrader(): List<Omrade> {
        val alleOmrader = geografiConsumer.hentAlleOmrader()
        val fylkerMedKommuner = alleOmrader
                .filter { it.nivaa == "2" }
                .filter { !erUtgått(it) }
                .map { dtoToOmrade(it, alleOmrader) }

        val andreRelevanteOmrader = listOf(
                Omrade(nivaa = "1", id = "resten av verden", navn = "Resten av verden", strukturkode = "resten av verden", underomrader = emptyList()),
                Omrade(nivaa = "1", id = "EOSEU", navn = "EØS", strukturkode = "EOSEU", underomrader = emptyList())
        )

        return fylkerMedKommuner.union(andreRelevanteOmrader).toList()
    }

    fun getKommunerForFylke(fylkeId: String): List<Omrade> =
        hentAlleRelevanteOmrader()
                .find { it.id == fylkeId }
                ?.underomrader
                ?: emptyList()

    private fun dtoToOmrade(dto: OmradeDTO, alleDto: List<OmradeDTO>): Omrade =
            Omrade(
                    id = dto.id,
                    nivaa = dto.nivaa,
                    navn = dto.navn,
                    strukturkode = dto.strukturkode,
                    parent = dto.parent,
                    underomrader = alleDto
                            .filter { it.parent == dto.id }
                            .map { dtoToOmrade(it, alleDto) }
            )

    private fun erUtgått(omrade: OmradeDTO) =
            omrade.navn.contains("Ikke i bruk") ||
                    omrade.navn.contains("UTGÅTT") ||
                    omrade.navn.contains("gml") ||
                    omrade.navn.contains("Gml") ||
                    omrade.navn.contains("gammel")
}