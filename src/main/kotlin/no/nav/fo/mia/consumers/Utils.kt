package no.nav.fo.mia.consumers


import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.AbstractQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.TermsQueryBuilder
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.BucketOrder
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum
import org.elasticsearch.search.builder.SearchSourceBuilder

fun Int.sensurer() = if (this < 4) null else this

internal fun periodeFilter(periode: String) = QueryBuilders.termQuery(perioder, periode)

internal fun komuneFilter(komuner: List<String>): AbstractQueryBuilder<*> {
    return if (komuner.isEmpty()) {
        QueryBuilders.matchAllQuery()
    } else {
        TermsQueryBuilder(komuneNumer, komuner)
    }
}

internal fun underkategoriFilter(underkategorier: List<String>): AbstractQueryBuilder<*> {
    return if (underkategorier.isEmpty()) {
        QueryBuilders.matchAllQuery()
    } else {
        TermsQueryBuilder(underkattegori, underkategorier)
    }
}

internal fun aktivPublicQuery(): AbstractQueryBuilder<*> {
    return must(
            QueryBuilders.termQuery(active, true),
            QueryBuilders.termQuery(public, true)
    )
}

internal fun must(vararg querys: AbstractQueryBuilder<*>): AbstractQueryBuilder<*> {
    return querys.fold(QueryBuilders.boolQuery()) { acc, query -> acc.must(query) }
}

internal fun RestHighLevelClient.sumPerBucket(
        filterQuery: AbstractQueryBuilder<*>,
        index: String,
        grupperingsKollone: String,
        summeringskollone: String,
        includeEmty: Boolean = true
): Map<String, Int> {
    val grupperingsNavn = "grupperingsnavn"
    val sumNavn = "anatll"

    val agregation = AggregationBuilders
            .terms(grupperingsNavn)
            .field(grupperingsKollone)
            .size(Int.MAX_VALUE)
            .subAggregation(
                    AggregationBuilders
                            .sum(sumNavn)
                            .field(summeringskollone)
            )


    if (includeEmty) {
        agregation
                .minDocCount(0)
    }


    val source = SearchRequest(index)
            .source(
                    SearchSourceBuilder()
                            .size(0)
                            .query(filterQuery)
                            .aggregation(agregation)
            )

    return this
            .search(source)
            .aggregations
            .get<ParsedStringTerms>(grupperingsNavn)
            .buckets
            .map {
                it.key as String to it.aggregations.get<ParsedSum>(sumNavn).value.toInt()
            }.toMap()
}

internal fun RestHighLevelClient.sum(
        filterQuery: AbstractQueryBuilder<*>,
        index: String,
        summeringskollone: String
): Int {
    val sumNavn = "sumNavn"

    val sorce =
            SearchRequest(index).source(SearchSourceBuilder()
                    .size(0)
                    .query(filterQuery)
                    .aggregation(AggregationBuilders
                            .sum(sumNavn)
                            .field(summeringskollone)))

    return this.search(sorce).aggregations.get<ParsedSum>(sumNavn).value.toInt()
}

internal fun RestHighLevelClient.max(
        vararg index: String,
        kolonne: String
): String {
    val bukket = "bukket"
    val agregation = AggregationBuilders
            .terms(bukket)
            .field(kolonne)
            .size(1)
            .order(BucketOrder.key(false))

    val serch = SearchSourceBuilder()
            .size(0)
            .query(QueryBuilders.matchAllQuery())
            .aggregation(agregation)

    val search = this.search(SearchRequest(index, serch))
    return search.aggregations.get<ParsedStringTerms>(bukket).buckets[0].key as String
}
