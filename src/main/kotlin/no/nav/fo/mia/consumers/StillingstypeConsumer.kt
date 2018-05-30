package no.nav.fo.mia.consumers

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

interface StillingstypeConsumer {
    fun getYrkgrLvl1Id(yrkgrLvl2Id: String): List<String>
    fun getStillingstyper(): Map<String, List<String>>
    fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<String>
}

@Service
@Profile("!mock")
class StillingstypeConsumerImpl : StillingstypeConsumer {
    override fun getYrkgrLvl1Id(yrkgrLvl2Id: String): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStillingstyper(): Map<String, List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

@Service
@Profile("mock")
class StillingstypeConsumerMock: StillingstypeConsumer {
    override fun getYrkgrLvl1Id(yrkgrLvl2Id: String): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStillingstyper(): Map<String, List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
