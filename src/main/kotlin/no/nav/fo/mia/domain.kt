package no.nav.fo.mia

data class Bransje (
        val navn: String,
        val id: String,
        val strukturkode: String,
        val parent: List<String> = emptyList(),
        val antallStillinger: Int
)

data class Omrade (
        val nivaa: String,
        val id: String,
        val navn: String,
        val strukturkode: String,
        val parent: String? = null,
        val underomrader: List<Omrade> = emptyList()
)

data class Stilling (
        val arbeidsgivernavn: String,
        val id: String,
        val tittel: String,
        val soknadsfrist: String? = null,
        val stillingstype: String,
        val antallStillinger: Int,
        val lokal: Boolean,
        val yrkesgrupper: List<String> = emptyList(),
        val yrkesomrader: List<String> = emptyList()
)

data class OmradeStilling (
        val id: String,
        val antallLedige: Int,
        val antallStillinger: Int
)
