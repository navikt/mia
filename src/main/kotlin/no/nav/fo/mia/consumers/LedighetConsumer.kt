package no.nav.fo.mia.consumers

import com.github.javafaker.Faker
import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.service.SolrGeografiMappingService
import no.nav.fo.mia.util.filtervalgToSeed
import no.nav.fo.mia.util.stringToSeed
import org.apache.commons.lang3.StringUtils
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.*
import javax.inject.Inject

interface LedighetConsumer {
    fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int>
    fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int>
    fun getLedighetForKommuner(periode: String, filtervalg: Filtervalg): Map<String, Int>
    fun getLedighetForFylker(periode: String, filtervalg: Filtervalg): Map<String, Int>
    fun getSisteOpplastedeMaaned(): String
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

    override fun getLedighetForKommuner(periode: String, filtervalg: Filtervalg): Map<String, Int> {
        val fylkesnr = filtervalg.fylker.mapNotNull { solrGeografiMappingService.getStrukturkodeForId(it) }
        val kommuneNr = filtervalg.kommuner.mapNotNull { solrGeografiMappingService.getStrukturkodeForId(it) }
        val solrQuery = createSolrQueryForFiltreringsvalg(
                yrkesomradeid = filtervalg.yrkesomrade,
                yrkesgrupper = filtervalg.yrkesgrupper,
                fylkesnr = fylkesnr,
                kommunenr = kommuneNr
        )

        solrQuery.
                addFilterQuery("PERIODE:$periode")
                .addFacetField("KOMMUNENR")

        return arbeidsledighetSolrClient.query(solrQuery).getFacetField("KOMMUNENR").values
                .filter { it.count > 0 }
                .map { solrGeografiMappingService.getIdForStrukturkode(it.name)!! to it.count.toInt() }
                .toMap()
    }

    override fun getLedighetForFylker(periode: String, filtervalg: Filtervalg): Map<String, Int> {
        val fylkesnr = filtervalg.fylker.mapNotNull { solrGeografiMappingService.getStrukturkodeForId(it) }
        val solrQuery = createSolrQueryForFiltreringsvalg(
                yrkesomradeid = filtervalg.yrkesomrade,
                yrkesgrupper = filtervalg.yrkesgrupper,
                fylkesnr = fylkesnr,
                kommunenr = emptyList()
        )

        solrQuery.
                addFilterQuery("PERIODE:$periode")
                .addFacetField("FYLKESNR")

        return arbeidsledighetSolrClient.query(solrQuery).getFacetField("FYLKESNR").values
                .filter { it.count > 0 }
                .map { solrGeografiMappingService.getIdForStrukturkode(it.name)!! to it.count.toInt() }
                .toMap()
    }

    override fun getSisteOpplastedeMaaned(): String {
        val query = SolrQuery("*:*")
                .addFacetField("PERIODE")
                .setRows(0)

        return arbeidsledighetSolrClient.query(query).getFacetField("PERIODE").values
                .map { it.name }
                .sorted()
                .last()
    }

    private fun getStatistikkSisteTrettenMaaneder(client: SolrClient, filtervalg: Filtervalg): Map<String, Int> {
        val query = createSolrQueryForFiltreringsvalg(
                yrkesomradeid = filtervalg.yrkesomrade,
                yrkesgrupper = filtervalg.yrkesgrupper,
                fylkesnr = filtervalg.fylker.mapNotNull { solrGeografiMappingService.getStrukturkodeForId(it) },
                kommunenr = filtervalg.kommuner.mapNotNull { solrGeografiMappingService.getStrukturkodeForId(it) }
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
    override fun getLedighetForKommuner(periode: String, filtervalg: Filtervalg): Map<String, Int> {
        val faker = Faker(Random(stringToSeed("ledighetKommune") + filtervalgToSeed(filtervalg)))
        return filtervalg.kommuner
                .map { it to faker.number().numberBetween(0, 1000) }
                .toMap()
    }

    override fun getLedighetForFylker(periode: String, filtervalg: Filtervalg): Map<String, Int> {
        val faker = Faker(Random(stringToSeed("ledighetFylke") + filtervalgToSeed(filtervalg)))
        return filtervalg.fylker
                .map { it to faker.number().numberBetween(0, 1000) }
                .toMap()
    }

    override fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> {
        val faker = Faker(Random(1 + filtervalgToSeed(filtervalg)))
        val min = faker.number().numberBetween(0, 100)
        val max = faker.number().numberBetween(min, 400)
        return getSistePerioder()
                .map { it to faker.number().numberBetween(min, max) }
                .toMap()
    }

    override fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> {
        val faker = Faker(Random(2 + filtervalgToSeed(filtervalg)))
        val min = faker.number().numberBetween(0, 100)
        val max = faker.number().numberBetween(min, 400)
        return getSistePerioder()
                .map { it to faker.number().numberBetween(min, max) }
                .toMap()
    }

    override fun getSisteOpplastedeMaaned(): String =
            getSistePerioder().last()

    private fun getSistePerioder(): List<String> =
            listOf("201701", "201702", "201703", "201704", "201705", "201706", "201707", "201708", "201709", "201710", "201711", "201712", "201801")
}