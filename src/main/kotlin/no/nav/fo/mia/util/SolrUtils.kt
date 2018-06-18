package no.nav.fo.mia.util

import no.nav.fo.mia.Filtervalg
import org.apache.solr.client.solrj.SolrQuery
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

fun solrQueryMedOmradeFilter(query: String = "*:*", filtervalg: Filtervalg): SolrQuery {
    val solrQuery = SolrQuery(query)
    val statements = ArrayList<String>()

    if (!filtervalg.fylker.isEmpty()) {
        statements.add("FYLKE_ID:${filtervalg.fylker.joinToString(" OR ")}")
    }
    if (!filtervalg.kommuner.isEmpty()) {
        statements.add("KOMMUNE_ID:${filtervalg.kommuner.joinToString(" OR ")}")
    }

    if (filtervalg.eoseu) {
        statements.add("LANDGRUPPE:EOSEU")
    }

    if (filtervalg.restenavverden) {
        statements.add("LANDGRUPPE:\"resten av verden\"")
    }

    if (!statements.isEmpty()) {
        solrQuery.addFilterQuery(statements.joinToString(" OR "))
    }
    return solrQuery
}

fun filterForYrker(yrkesomrade: String?, yrkesgrupper: List<String>): List<String> {
    val filter = ArrayList<String>()
    if (yrkesomrade != null) {
        filter.add("YRKGR_LVL_1_ID:${yrkesomrade}")
    }
    if (yrkesgrupper.isNotEmpty()) {
        filter.add("YRKGR_LVL_2_ID:(${yrkesgrupper.joinToString(" OR ")})")
    }
    return filter
}

fun dateToString(date: Date?): String? =
        date?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDateTime()?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
