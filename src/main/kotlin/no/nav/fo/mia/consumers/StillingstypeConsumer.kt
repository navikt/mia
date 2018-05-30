package no.nav.fo.mia.consumers

interface StillingstypeConsumer {
    fun getYrkgrLvl1Id(yrkgrLvl2Id: String): List<String>
    fun getStillingstyper(): Map<String, List<String>>
    fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<String>
}