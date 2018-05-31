package no.nav.fo.mia.consumers

import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.inject.Inject

interface StillingstypeConsumer {
    fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<YrkesgruppeDTO>
}

@Service
@Profile("!mock")
class StillingstypeConsumerImpl @Inject
constructor (
        val supportSolrClient: SolrClient
) : StillingstypeConsumer {

    override fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<YrkesgruppeDTO> {
        val query = SolrQuery("*:*")
                .addFilterQuery("PARENT:$yrkesomradeid")
                .addFilterQuery("DOKUMENTTYPE:STILLINGSTYPE")

        return supportSolrClient.query(query).results.map {
            YrkesgruppeDTO(
                    id = it.getFieldValue("ID") as String,
                    navn = it.getFieldValue("NAVN") as String,
                    strukturkode = it.getFieldValue("STRUKTURKODE") as String?,
                    parents = it.getFieldValues("PARENT").map { it as String }
            )
        }
    }
}

@Service
@Profile("mock")
class StillingstypeConsumerMock: StillingstypeConsumer {
    override fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<YrkesgruppeDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
