package no.nav.fo.mia.service

import no.nav.fo.mia.consumers.StillingstypeConsumer
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class SolrBransjeMappingService @Inject
constructor (
        val stillingstypeConsumer: StillingstypeConsumer
) {
    //private val strukturkodeTilYrkgrLvl2Mapoing = createStrukturkodeTilYrkgrLvl2Mapping()
    //private val yrkgrLvl2TilStrukturkodeMapping = createYrkgrLvl2TilStrukturkodeMapping()
    //private val yrkgrLvl2TilYrkgrLvl1Mapping = createYrkgrLvl2TilYrkgrLvl1Mapping()


    private fun createStrukturkodeTilYrkgrLvl2Mapping(): Map<String, List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun createYrkgrLvl2TilStrukturkodeMapping(): Map<String, List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun createYrkgrLvl2TilYrkgrLvl1Mapping(): Map<String, List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}