package no.nav.fo.mia.consumers

data class OmradeDTO (
        val id: String,
        val strukturkode: String,
        val nivaa: String,
        val navn: String,
        val parent: String?
)