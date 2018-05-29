package no.nav.fo.mia.consumers

interface StillingstypeConsumer {
    fun getYrkgrLvl1IdFraSolr(yrkgrLvl2Id: String): List<String>
    fun getStillingstyperFraSolr(): Map<String, List<String>>
    fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<String>
}