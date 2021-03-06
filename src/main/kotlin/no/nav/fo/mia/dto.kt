package no.nav.fo.mia

import javax.ws.rs.QueryParam

data class Filtervalg (
    @QueryParam("yrkesomrade")
    var yrkesomrade: String? = null,

    @QueryParam("yrkesgrupper")
    var yrkesgrupper: List<String> = emptyList(),

    @QueryParam("fylker")
    var fylker: List<String> = emptyList(),

    @QueryParam("kommuner")
    var kommuner: List<String> = emptyList(),

    @QueryParam("eoseu")
    var eoseu: Boolean = false,

    @QueryParam("restenavverden")
    var restenavverden: Boolean = false
)

data class Feilmelding(val id: String, val tittel: String, val message: String, val callId: String?)
