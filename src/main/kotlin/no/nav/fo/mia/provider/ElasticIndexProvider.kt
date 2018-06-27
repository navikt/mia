package no.nav.fo.mia.provider

import no.nav.fo.mia.util.ElasticConstants.Companion.arbeidledige
import no.nav.fo.mia.util.ElasticConstants.Companion.arbeidsledigeIndex
import no.nav.fo.mia.util.ElasticConstants.Companion.doc
import no.nav.fo.mia.util.ElasticConstants.Companion.fylkesnummer
import no.nav.fo.mia.util.ElasticConstants.Companion.komunenummer
import no.nav.fo.mia.util.ElasticConstants.Companion.ledigeStillinger
import no.nav.fo.mia.util.ElasticConstants.Companion.periode
import no.nav.fo.mia.util.ElasticConstants.Companion.propertys
import no.nav.fo.mia.util.ElasticConstants.Companion.stillingerIndex
import no.nav.fo.mia.util.ElasticConstants.Companion.yrkesgruppe_lvl_1
import no.nav.fo.mia.util.ElasticConstants.Companion.yrkesgruppe_lvl_2
import no.nav.fo.mia.util.ElasticConstants.Companion.yrkeskode
import org.apache.http.util.EntityUtils
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.client.RestHighLevelClient
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

interface ElasticIndexProvider {
    fun getAllIndexes(): String
    fun index(bulk: BulkRequest)
    fun getCluseterInfo(): String
    fun createIndexForAlias(alias: String): String
    fun replaceIndexForAlias(alias: String, nyIndex: String)
}

@Service
@Profile("!mock")
class ElasticIndexProviderImpl @Inject
constructor(
        val client: RestHighLevelClient
) : ElasticIndexProvider {
    private val LOGGER = LoggerFactory.getLogger(ElasticIndexProviderImpl::class.java)

    override fun createIndexForAlias(alias: String) = when (alias) {
        arbeidsledigeIndex -> createArbeidsledigeIndex()
        stillingerIndex -> createStillingerIndex()
        else -> throw error("$alias finnes ikke")
    }

    override fun getCluseterInfo(): String {
        val response = client.lowLevelClient.performRequest("GET", "/")
        return EntityUtils.toString(response.entity)
    }

    override fun index(bulk: BulkRequest) {
        val response = client.bulk(bulk)
        if(response.hasFailures()){
            throw RuntimeException(response.buildFailureMessage())
        }
    }

    override fun replaceIndexForAlias(alias: String, nyIndex: String) {
        val removeOldIndexes = AliasActions(AliasActions.Type.REMOVE)
                .alias(alias)
                .index("*")

        val addNewIndex = AliasActions(AliasActions.Type.ADD)
                .alias(alias)
                .index(nyIndex)

        val request = IndicesAliasesRequest()
                .addAliasAction(removeOldIndexes)
                .addAliasAction(addNewIndex)

        client
                .indices()
                .updateAliases(request)

        client
                .indices()
                .delete(DeleteIndexRequest("${alias}_*,-$nyIndex"))
    }

    override fun getAllIndexes(): String {
        val response = client.lowLevelClient.performRequest("GET", "/_cat/indices?v")
        return EntityUtils.toString(response.entity)
    }

    private fun createArbeidsledigeIndex(): String {
        val properties = HashMap<String, Any>()

        properties[periode] = keyword
        properties[fylkesnummer] = keyword
        properties[komunenummer] = keyword
        properties[yrkeskode] = keyword
        properties[arbeidledige] = integer
        properties[yrkesgruppe_lvl_1] = keyword
        properties[yrkesgruppe_lvl_2] = keyword

        return createIndex(alias = arbeidsledigeIndex, properties = properties)
    }

    private fun createStillingerIndex(): String {
        val properties = HashMap<String, Any>()

        properties[periode] = keyword
        properties[fylkesnummer] = keyword
        properties[komunenummer] = keyword
        properties[yrkeskode] = keyword
        properties[ledigeStillinger] = integer
        properties[yrkesgruppe_lvl_1] = keyword
        properties[yrkesgruppe_lvl_2] = keyword

        return createIndex(alias = stillingerIndex, properties = properties)
    }

    private fun createIndex(alias: String, properties: HashMap<String, Any>): String {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm")
        val index = alias + "_" + LocalDateTime.now().format(formatter)

        LOGGER.info("Lager index: $index for alias: $alias")

        val request = CreateIndexRequest(index)
        request.mapping(doc, wrapPropertys(doc, properties))

        client.indices().create(request)

        LOGGER.info("Index: $index opprettet for alias: $alias")
        return index
    }

    private fun wrapPropertys(type: String, properties: HashMap<String, Any>): Map<String, Any> =
            mapOf(type to mapOf(propertys to properties))

    private val keyword = hashMapOf<String, Any>("type" to "keyword")
    private val integer = hashMapOf<String, Any>("type" to "integer")
}

@Service
@Profile("mock")
class ElasticIndexProviderMock : ElasticIndexProvider {
    override fun replaceIndexForAlias(alias: String, nyIndex: String) {
    }

    override fun createIndexForAlias(alias: String) = "created-mock-index"
    override fun getCluseterInfo() = "mock info"
    override fun getAllIndexes() = "MOCK indexes"

    override fun index(bulk: BulkRequest) {
    }
}
