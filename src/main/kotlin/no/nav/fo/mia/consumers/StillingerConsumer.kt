package no.nav.fo.mia.consumers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.Stilling
import no.nav.fo.mia.util.dateToString
import no.nav.fo.mia.util.filterForYrker
import no.nav.fo.mia.util.solrQueryMedOmradeFilter
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.response.FacetField
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.lang.Integer.parseInt
import java.util.*
import javax.inject.Inject

interface StillingerConsumer {
    fun getAntallStillingerForYrkesomrade(yrkesomrade: String, filtervalg: Filtervalg): Int
    fun getAntallStillingerForYrkesgruppe(yrkesgruppe: String, filtervalg: Filtervalg): Int
    fun getAntallStillingerForValgtOmrade(filtervalg: Filtervalg): Int
    fun getStillingsannonser(yrkesgrupper: List<String>, filtervalg: Filtervalg): List<Stilling>
    fun getLedigeStillingerForKommune(kommune: String, filtervalg: Filtervalg): Int
    fun getLedigeStillingerForFylke(fylke: String, filtervalg: Filtervalg): Int
}

@Service
@Profile("!mock")
class StillingerConsumerImpl @Inject
constructor (
        val stillingSolrClient: SolrClient
): StillingerConsumer {
    override fun getLedigeStillingerForFylke(fylke: String, filtervalg: Filtervalg): Int =
            hentLedigeStillingerForOmrade("FYLKE_ID:$fylke", filtervalg)

    override fun getLedigeStillingerForKommune(kommune: String, filtervalg: Filtervalg): Int =
            hentLedigeStillingerForOmrade("KOMMUNE_ID:$kommune", filtervalg)

    override fun getStillingsannonser(yrkesgrupper: List<String>, filtervalg: Filtervalg): List<Stilling> {
        val query = solrQueryMedOmradeFilter("*:*", filtervalg)
                .addFilterQuery(yrkesgrupper.joinToString(" OR ") { "YRKGR_LVL_2_ID:$it" })
                .setRows(Int.MAX_VALUE)

        return stillingSolrClient.query(query).results
                .map {
                    Stilling(
                            id = (it.getFieldValue("ID") as Int).toString(),
                            arbeidsgivernavn = it.getFieldValue("ARBEIDSGIVERNAVN") as String,
                            tittel = it.getFieldValue("TITTEL") as String,
                            stillingstype = it.getFieldValue("STILLINGSTYPE_5") as String,
                            lokal = "LOK" == it.getFieldValue("PRESENTASJONSFORMKODE"),
                            antallStillinger = (it.getFieldValue("ANTALLSTILLINGER") as Int?) ?: 1,
                            soknadsfrist = dateToString(it.getFieldValue("SOKNADSFRIST") as Date?),
                            yrkesgrupper = it.getFieldValues("YRKGR_LVL_2_ID").map { it.toString() },
                            yrkesomrader = it.getFieldValues("YRKGR_LVL_1_ID").map { it.toString() }
                    )
                }
    }

    override fun getAntallStillingerForValgtOmrade(filtervalg: Filtervalg): Int =
            getAntallStillinger("*:*", filtervalg)

    override fun getAntallStillingerForYrkesgruppe(yrkesgruppe: String, filtervalg: Filtervalg): Int =
            getAntallStillinger("YRKGR_LVL_2_ID:$yrkesgruppe", filtervalg)

    override fun getAntallStillingerForYrkesomrade(yrkesomrade: String, filtervalg: Filtervalg): Int =
            getAntallStillinger("YRKGR_LVL_1_ID:$yrkesomrade", filtervalg)

    private fun hentLedigeStillingerForOmrade(query: String, filtervalg: Filtervalg): Int {
        val solrQuery = SolrQuery("*:*")
                .addFilterQuery(query)
                .setRows(0)
                .addFacetField("ANTALLSTILLINGER")

        filterForYrker(yrkesomrade = filtervalg.yrkesomrade, yrkesgrupper = filtervalg.yrkesgrupper)
                .forEach { solrQuery.addFilterQuery(it) }

        return getAntallStillingerFraFacet(stillingSolrClient.query(solrQuery).getFacetField("ANTALLSTILLINGER"))
    }

    private fun getAntallStillinger(filter: String, filtervalg: Filtervalg): Int {
        val query = solrQueryMedOmradeFilter(filtervalg = filtervalg)
                .addFilterQuery(filter)
                .addFacetField("ANTALLSTILLINGER")
                .setRows(0)

        return getAntallStillingerFraFacet(stillingSolrClient.query(query).getFacetField("ANTALLSTILLINGER"))
    }

    private fun getAntallStillingerFraFacet(antallStillingerFacet: FacetField?): Int {
        if (antallStillingerFacet == null || antallStillingerFacet.valueCount == 0) {
            return 0
        }

        return antallStillingerFacet.values
                .map { it.count * parseInt(it.name ?: "1") }
                .sum().toInt()
    }
}

@Service
@Profile("mock")
class StillingerConsumerMock: StillingerConsumer {
    override fun getAntallStillingerForYrkesomrade(yrkesomrade: String, filtervalg: Filtervalg): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAntallStillingerForYrkesgruppe(yrkesgruppe: String, filtervalg: Filtervalg): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAntallStillingerForValgtOmrade(filtervalg: Filtervalg): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStillingsannonser(yrkesgrupper: List<String>, filtervalg: Filtervalg): List<Stilling> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedigeStillingerForKommune(kommune: String, filtervalg: Filtervalg): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedigeStillingerForFylke(fylke: String, filtervalg: Filtervalg): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}