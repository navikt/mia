package no.nav.fo.mia.consumers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.service.SolrGeografiMappingService
import org.apache.commons.lang3.StringUtils
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.inject.Inject

interface LedighetConsumer {
    fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int>
    fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int>
    fun getSisteOpplastedeMaaned(): String
    fun getLedighetForKommuner(filtervalg: Filtervalg): Map<String, Int>
    fun getLedighetForFylker(filtervalg: Filtervalg): Map<String, Int>
}

@Service
@Profile("!mock")
class LedighetConsumerImpl @Inject
constructor (
        val arbeidsledighetSolrClient: SolrClient,
        val ledigestillingSolrClient: SolrClient,
        val solrGeografiMappingService: SolrGeografiMappingService
): LedighetConsumer {
    override fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> =
            getStatistikkSisteTrettenMaaneder(arbeidsledighetSolrClient, filtervalg)

    override fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> =
            getStatistikkSisteTrettenMaaneder(ledigestillingSolrClient, filtervalg)

    override fun getSisteOpplastedeMaaned(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetForKommuner(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetForFylker(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getStatistikkSisteTrettenMaaneder(client: SolrClient, filtervalg: Filtervalg): Map<String, Int> {
        val query = createSolrQueryForFiltreringsvalg(
                yrkesomradeid = filtervalg.yrkesomrade,
                yrkesgrupper = filtervalg.yrkesgrupper,
                fylkesnr = filtervalg.fylker.map { solrGeografiMappingService.getStrukturkodeForId(it) }.filterNotNull(),
                kommunenr = filtervalg.kommuner.map { solrGeografiMappingService.getStrukturkodeForId(it) }.filterNotNull()
        )
        query.addFacetField("PERIODE").rows = 0

        return client.query(query).getFacetField("PERIODE").values
                .map { it.name to it.count.toInt() }
                .toMap()
    }

    private fun createSolrQueryForFiltreringsvalg(yrkesomradeid: String?, yrkesgrupper: List<String>, fylkesnr: List<String>, kommunenr: List<String>): SolrQuery {
        val solrQuery = SolrQuery("*:*")
        solrQuery.rows = 0

        if (!yrkesgrupper.isEmpty()) {
            solrQuery.addFilterQuery(String.format("YRKGR_LVL_2_ID:(%s)", StringUtils.join(yrkesgrupper, " OR ")))
        } else if (yrkesomradeid != null) {
            solrQuery.addFilterQuery("YRKGR_LVL_1_ID:$yrkesomradeid")
        }

        solrQuery.addFilterQuery(createFylkeFilter(fylkesnr, kommunenr))
        return solrQuery
    }

    private fun createFylkeFilter(fylker: List<String>, kommuner: List<String>): String {
        var filter = ""

        if (fylker.isNotEmpty()) {
            filter += "FYLKESNR:${fylker.joinToString(" OR ")}"
        }

        if (fylker.isNotEmpty() && kommuner.isNotEmpty()) {
            filter += " OR "
        }

        if (kommuner.isNotEmpty()) {
            filter += "KOMMUNENR:${kommuner.joinToString(" OR ")}"
        }
        return filter
    }
}

@Service
@Profile("mock")
class LedighetConsumerMock: LedighetConsumer {
    override fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSisteOpplastedeMaaned(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetForKommuner(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetForFylker(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}