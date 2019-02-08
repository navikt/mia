package no.nav.fo.mia.controllers

import no.nav.fo.mia.util.fylkeTilKommuner
import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.Omrade
import no.nav.fo.mia.util.fylkesnrTilNavn
import no.nav.fo.mia.util.hovedkategoriTilUnderkategori
import no.nav.fo.mia.util.kommuneNrTIlNavn


fun kommunerFra(filtervalg: Filtervalg) = filtervalg
        .fylker
        .mapNotNull { fylkeTilKommuner[it] }
        .flatten()
        .union(filtervalg.kommuner)
        .toList() //TODO fiks utenfor norge og andre områder

fun underkategorierFra(filtervalg: Filtervalg) =
        if (filtervalg.yrkesgrupper.isEmpty()) {
            hovedkategoriTilUnderkategori[filtervalg.yrkesomrade] ?: emptyList()
        } else filtervalg.yrkesgrupper

fun alleRelevanteOmråder() = fylkesnrTilNavn.map { fylke ->
    Omrade(
            nivaa = "2",
            id = fylke.key,
            navn = fylke.value,
            strukturkode = fylke.key,
            parent = null,
            underomrader = fylkeTilKommuner[fylke.key]!!.map { kommune ->
                Omrade(
                        nivaa = "3",
                        id = kommune,
                        navn = kommuneNrTIlNavn[kommune]!!,
                        strukturkode = kommune,
                        parent = fylke.key,
                        underomrader = emptyList()
                )
            }
    )
}
