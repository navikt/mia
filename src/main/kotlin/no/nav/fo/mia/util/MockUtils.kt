package no.nav.fo.mia.util

import no.nav.fo.mia.Filtervalg

fun stringToSeed(text: String): Long =
        text.map { it.toLong() }
                .fold(11L) { acc, i -> (acc * 31 ) + i }

fun filtervalgToSeed(filtervalg: Filtervalg): Long {
    val fylker = filtervalg.fylker.sorted().joinToString("")
    val kommuner = filtervalg.kommuner.sorted().joinToString("")
    val yrkesgrupper = filtervalg.yrkesgrupper.sorted().joinToString("")
    val yrkesomrade = filtervalg.yrkesomrade ?: ""

    return stringToSeed(fylker + kommuner + yrkesgrupper + yrkesomrade)
}