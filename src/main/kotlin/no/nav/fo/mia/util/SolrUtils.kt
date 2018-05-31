package no.nav.fo.mia.util

import no.nav.fo.mia.Filtervalg
import org.apache.solr.client.solrj.SolrQuery
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun solrQueryMedOmradeFilter(query: String = "*:*", filtervalg: Filtervalg): SolrQuery {
    val solrQuery = SolrQuery("*:*")
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

fun dateToString(date: Date): String? {
    return date.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDateTime()?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}
