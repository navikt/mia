package no.nav.fo.mia.provider

import org.apache.http.util.EntityUtils
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.inject.Inject

interface ElasticIndexProvider{
    fun createArbeidsledigeIndex()
    fun createStillingerIndex()
    fun getAll(): String
}

@Service
@Profile("!mock")
class ElasticIndexProviderImpl @Inject
constructor(
        val client: RestHighLevelClient
): ElasticIndexProvider {
    override fun createArbeidsledigeIndex() {
        val request = CreateIndexRequest("arbeidsledige")
        val map = HashMap<String, Any>()

        map["PERIODE"] = "keyword"
        map["FYLKESNR"] = "keyword"
        map["KOMMUNENR"] = "keyword"
        map["YRKESKODE"] = "keyword"
        map["ARBEIDSLEDIGE"] = "integer"
        map["YRKGR_LVL_1_ID"] = "keyword"
        map["YRKGR_LVL_2_ID"] = "keyword"

        request.mapping("arbeidsledige", map)

        val response = client.indices().create(request)
        validateResponse(response)
    }

    override fun createStillingerIndex() {
        val request = CreateIndexRequest("stillinger")
        val map = HashMap<String, Any>()

        map["PERIODE"] = "keyword"
        map["FYLKESNR"] = "keyword"
        map["KOMMUNENR"] = "keyword"
        map["YRKESKODE"] = "keyword"
        map["LEDIGE_STILLINGER"] = "integer"
        map["YRKGR_LVL_1_ID"] = "keyword"
        map["YRKGR_LVL_2_ID"] = "keyword"

        request.mapping("stillinger", map)

        val response = client.indices().create(request)
        validateResponse(response)
    }

    override fun getAll(): String {
        val searchRequest = SearchRequest()
        val searchSourceBuilder = SearchSourceBuilder()
        searchSourceBuilder.query(QueryBuilders.matchAllQuery())
        searchRequest.source(searchSourceBuilder)
        val search = client.search(searchRequest)
        val response = client.lowLevelClient.performRequest("GET", "/_cat/indices?v")
        return EntityUtils.toString(response.entity)
    }

    private fun validateResponse(response: CreateIndexResponse) {
        if(!response.isAcknowledged) {

        }
        if(!response.isShardsAcked) {

        }
    }

}
