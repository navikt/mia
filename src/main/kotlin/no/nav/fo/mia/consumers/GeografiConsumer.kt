package no.nav.fo.mia.consumers

import no.nav.fo.mia.Omrade

interface GeografiConsumer {
    fun getFylkerOgKommuner(): List<Omrade>
    fun finnKommunerTilFylker(fylker: List<String>): List<String>
}