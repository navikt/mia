package no.nav.fo.mia.consumers

data class OmradeDTO (
        val id: String,
        val strukturkode: String,
        val nivaa: String,
        val navn: String,
        val parent: String?
)

data class YrkesomradeDTO (
        val id: String,
        val navn: String
)

data class YrkesgruppeDTO (
        val id: String,
        val navn: String,
        val strukturkode: String,
        val parents: List<String>
)