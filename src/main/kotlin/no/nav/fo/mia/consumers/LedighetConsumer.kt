package no.nav.fo.mia.consumers

import com.github.javafaker.Faker
import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.service.SolrGeografiMappingService
import no.nav.fo.mia.util.*
import no.nav.fo.mia.util.ElasticConstants.Companion.arbeidledige
import no.nav.fo.mia.util.ElasticConstants.Companion.arbeidsledigeIndex
import no.nav.fo.mia.util.ElasticConstants.Companion.fylkesnummer
import no.nav.fo.mia.util.ElasticConstants.Companion.komunenummer
import no.nav.fo.mia.util.ElasticConstants.Companion.ledigeStillinger
import no.nav.fo.mia.util.ElasticConstants.Companion.periode
import no.nav.fo.mia.util.ElasticConstants.Companion.stillingerIndex
import no.nav.fo.mia.util.ElasticConstants.Companion.yrkesgruppe_lvl_1
import no.nav.fo.mia.util.ElasticConstants.Companion.yrkesgruppe_lvl_2
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.*
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.BucketOrder
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.*
import javax.inject.Inject


interface LedighetConsumer {
    fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int>
    fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int>
    fun getArbeidsledighetPerKommuner(periode: String, filtervalg: Filtervalg): Map<String, Int>
    fun getArbeidsledighetPerFylker(periode: String, filtervalg: Filtervalg): Map<String, Int>
    fun getSisteOpplastedeMaaned(): String
}

@Service
@Profile("!mock")
class LedighetConsumerImpl @Inject
constructor(
        private val client: RestHighLevelClient,
        private val solrGeografiMappingService: SolrGeografiMappingService
) : LedighetConsumer {
    override fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> {
        val query = createQueryForFiltreringsvalg(filtervalg)

        return getStatestikk(
                query = query,
                gruperingsKolone = periode,
                summeringsKollone = arbeidledige,
                index = arbeidsledigeIndex
        )
    }

    override fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> {
        val query = createQueryForFiltreringsvalg(filtervalg)

        return getStatestikk(
                query = query,
                gruperingsKolone = periode,
                summeringsKollone = ledigeStillinger,
                index = stillingerIndex
        )
    }

    override fun getArbeidsledighetPerKommuner(periode: String, filtervalg: Filtervalg): Map<String, Int> {
        val query = createQuery(filtervalg = filtervalg, periode = periode)

        val statestikk = getStatestikk(
                query = query,
                gruperingsKolone = komunenummer,
                summeringsKollone = arbeidledige,
                index = arbeidsledigeIndex
        )
        return statestikk.mapKeys { solrGeografiMappingService.getIdForStrukturkode(it.key)!! }
    }


    override fun getArbeidsledighetPerFylker(periode: String, filtervalg: Filtervalg): Map<String, Int> {
        val filterUtenKomuner = filtervalg.copy(kommuner = emptyList())
        val query = createQuery(filterUtenKomuner, periode)

        val statestikk = getStatestikk(
                query = query,
                gruperingsKolone = fylkesnummer,
                summeringsKollone = arbeidledige,
                index = arbeidsledigeIndex
        )

        return statestikk.mapKeys { solrGeografiMappingService.getIdForStrukturkode(it.key)!! }
    }

    override fun getSisteOpplastedeMaaned(): String {
        val agregation = AggregationBuilders
                .terms("perioder")
                .field(periode)
                .size(1)
                .order(BucketOrder.key(false))

        val serch = SearchSourceBuilder()
                .size(0)
                .query(QueryBuilders.matchAllQuery())
                .aggregation(agregation)

        val search = client.search(SearchRequest(arbeidsledigeIndex, stillingerIndex).source(serch))
        return search.aggregations.get<ParsedStringTerms>("perioder").buckets[0].key as String

    }

    private fun getStatestikk(query: QueryBuilder, gruperingsKolone: String, summeringsKollone: String, index: String): Map<String, Int> {
        val summeringsnavn = "antall"
        val grupperingsnavn = "gruppering"

        val sumagregation = AggregationBuilders
                .sum(summeringsnavn)
                .field(summeringsKollone)

        val agregation = AggregationBuilders
                .terms(grupperingsnavn)
                .field(gruperingsKolone)
                .size(Int.MAX_VALUE)
                .subAggregation(sumagregation)

        val searchSourceBuilder = SearchSourceBuilder()
                .size(0)
                .query(query)
                .aggregation(agregation)

        val searchRequest = SearchRequest(index)
                .source(searchSourceBuilder)

        val response = client.search(searchRequest)

        return response.aggregations.get<ParsedStringTerms>(grupperingsnavn).buckets.map {
            it.key as String to it.aggregations.get<ParsedSum>(summeringsnavn).value.toInt()
        }.toMap()
    }

    private fun createQueryForFiltreringsvalg(filtervalg: Filtervalg) = createQuery(filtervalg, null)

    private fun createQuery(filtervalg: Filtervalg, periode: String?) :QueryBuilder = BoolQueryBuilder()
            .filter(arbeidsFilter(filtervalg))
            .filter(fylkeFilter(filtervalg))
            .filter(periodeFilter(periode))

    private fun arbeidsFilter(filtervalg: Filtervalg): QueryBuilder {
        return when {
            !filtervalg.yrkesgrupper.isEmpty() -> TermsQueryBuilder(yrkesgruppe_lvl_2, filtervalg.yrkesgrupper)
            filtervalg.yrkesomrade != null -> TermQueryBuilder(yrkesgruppe_lvl_1, filtervalg.yrkesomrade)
            else -> MatchAllQueryBuilder()
        }
    }

    private fun fylkeFilter(filtervalg: Filtervalg): QueryBuilder {
        val builder = BoolQueryBuilder()

        val fylker = filtervalg.fylker.mapNotNull { solrGeografiMappingService.getStrukturkodeForId(it) }
        val kommuner = filtervalg.kommuner.mapNotNull { solrGeografiMappingService.getStrukturkodeForId(it) }

        if (!fylker.isEmpty()) {
            builder.should(TermsQueryBuilder(fylkesnummer, fylker))
        }

        if (!kommuner.isEmpty()) {
            builder.should(TermsQueryBuilder(komunenummer, kommuner))
        }

        return builder
    }

    private fun periodeFilter(periode: String?): QueryBuilder {
        return when {
            periode != null -> TermQueryBuilder(ElasticConstants.periode, periode)
            else -> MatchAllQueryBuilder()
        }
    }
}

@Service
@Profile("mock")
class LedighetConsumerMock : LedighetConsumer {
    override fun getArbeidsledighetPerKommuner(periode: String, filtervalg: Filtervalg): Map<String, Int> {
        val faker = Faker(Random(stringToSeed("ledighetKommune") + filtervalgToSeed(filtervalg)))
        return filtervalg.kommuner
                .map { it to faker.number().numberBetween(0, 1000) }
                .toMap()
    }

    override fun getArbeidsledighetPerFylker(periode: String, filtervalg: Filtervalg): Map<String, Int> {
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
