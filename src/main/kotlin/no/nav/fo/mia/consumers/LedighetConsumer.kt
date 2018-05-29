package no.nav.fo.mia.consumers

import no.nav.fo.mia.Filtervalg

interface LedighetConsumer {
    fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int>
    fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int>
    fun getSisteOpplastedeMaaned(): String
    fun getLedighetForKommuner(yrkesomradeid: String, yrkesgrupper: List<String>, fylker: List<String>, kommuner: List<String>, periode: String): Map<String, Int>
    fun getLedighetForFylker(yrkesomradeid: String, yrkesgrupper: List<String>, fylker: List<String>, periode: String): Map<String, Int>
}