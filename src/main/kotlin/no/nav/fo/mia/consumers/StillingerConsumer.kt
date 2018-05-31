package no.nav.fo.mia.consumers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.util.solrQueryMedOmradeFilter
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.lang.Integer.parseInt
import javax.inject.Inject

interface StillingerConsumer {
    fun getYrkesomrader(): List<YrkesomradeDTO>
    fun getAntallStillingerForYrkesomrade(yrkesomrade: String, filtervalg: Filtervalg): Int
    fun getAntallStillingerForYrkesgruppe(yrkesomrade: String, filtervalg: Filtervalg): Int
    fun getAntallStillingerForValgtOmrade(filtervalg: Filtervalg): Int
}

@Service
@Profile("!mock")
class StillingerConsumerImpl @Inject
constructor (
        val stillingSolrClient: SolrClient
): StillingerConsumer {
    override fun getAntallStillingerForValgtOmrade(filtervalg: Filtervalg): Int =
            getAntallStillinger("*:*", filtervalg)

    override fun getAntallStillingerForYrkesgruppe(yrkesgruppe: String, filtervalg: Filtervalg): Int =
            getAntallStillinger("YRKGR_LVL_2_ID:$yrkesgruppe", filtervalg)

    override fun getAntallStillingerForYrkesomrade(yrkesomrade: String, filtervalg: Filtervalg): Int =
            getAntallStillinger("YRKGR_LVL_1_ID:$yrkesomrade", filtervalg)



    private fun getAntallStillinger(filter: String, filtervalg: Filtervalg): Int {
        val query = solrQueryMedOmradeFilter(filtervalg = filtervalg)
                .addFilterQuery(filter)
                .addFacetField("ANTALLSTILLINGER")
                .setRows(0)

        val antallStillingerFacet = stillingSolrClient.query(query).getFacetField("ANTALLSTILLINGER")
        if (antallStillingerFacet == null || antallStillingerFacet.valueCount == 0) {
            return 0
        }

        return antallStillingerFacet.values
                .map { it.count * parseInt(it.name?: "1")}
                .sum().toInt()
    }

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
}

@Service
@Profile("mock")
class StillingerConsumerMock: StillingerConsumer {
    override fun getAntallStillingerForValgtOmrade(filtervalg: Filtervalg): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getYrkesomrader(): List<YrkesomradeDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAntallStillingerForYrkesomrade(yrkesomrade: String, filtervalg: Filtervalg): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAntallStillingerForYrkesgruppe(yrkesomrade: String, filtervalg: Filtervalg): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}