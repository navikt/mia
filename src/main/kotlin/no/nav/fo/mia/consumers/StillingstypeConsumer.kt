package no.nav.fo.mia.consumers

import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.inject.Inject

interface StillingstypeConsumer {
    fun getYrkesomrader(): List<YrkesomradeDTO>
    fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<YrkesgruppeDTO>
    fun getAlleYrkesgrupperOgYrkesomrader(): List<YrkesgruppeDTO>
}

@Service
@Profile("!mock")
class StillingstypeConsumerImpl @Inject
constructor (
        val supportSolrClient: SolrClient,
        val stillingSolrClient: SolrClient
) : StillingstypeConsumer {
    override fun getYrkesomrader(): List<YrkesomradeDTO> {
        val query = SolrQuery("*:*")
        query.addFacetField("YRKGR_LVL_1")
                .addFacetField("YRKGR_LVL_1_ID")
                .rows = 0

        val response = stillingSolrClient.query(query)
        val navnFacets = response.getFacetField("YRKGR_LVL_1")
        val idFacets = response.getFacetField("YRKGR_LVL_1_ID")

        return (0 until navnFacets.valueCount).map {
            YrkesomradeDTO(
                    id = idFacets.values[it]?.name ?: "",
                    navn = navnFacets.values[it]?.name ?: ""
            )
        }
    }

    override fun getAlleYrkesgrupperOgYrkesomrader(): List<YrkesgruppeDTO> =
            getStillingstyper("*:*")

    override fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<YrkesgruppeDTO> =
            getStillingstyper("PARENT:$yrkesomradeid")

    private fun getStillingstyper(query: String): List<YrkesgruppeDTO> {
        val solrQuery = SolrQuery(query)
                .addFilterQuery("DOKUMENTTYPE:STILLINGSTYPE")

        return supportSolrClient.query(solrQuery).results.map {
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
    override fun getYrkesomrader(): List<YrkesomradeDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAlleYrkesgrupperOgYrkesomrader(): List<YrkesgruppeDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<YrkesgruppeDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
