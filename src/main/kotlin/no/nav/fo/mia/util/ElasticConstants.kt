package no.nav.fo.mia.util


class ElasticConstants {
    companion object {
        val stillingerHeader =  """"År-måned","Arbeidssted fylkesnummer","Arbeidssted kommunenummer","Stilling Yrkesbetegnelse kode (6-siffer)","Tilgang stillinger i perioden""""
        val arbeidsledigeHeader = """"År-måned","Person fylkeskommunenr","Person kommunenr","Praksis yrkesbetegnelse kode (6-siffer)","Beh personer i mnd""""

        const val periode = "PERIODE"
        const val fylkesnummer = "FYLKESNR"
        const val komunenummer = "KOMMUNENR"
        const val yrkeskode = "YRKESKODE"
        const val arbeidledige = "ARBEIDSLEDIGE"
        const val yrkesgruppe_lvl_1 = "YRKGR_LVL_1_ID"
        const val yrkesgruppe_lvl_2 = "YRKGR_LVL_2_ID"
        const val ledigeStillinger = "LEDIGE_STILLINGER"

        const val arbeidsledigeIndex = "arbeidsledige"
        const val stillingerIndex = "ledigestillinger"

        const val doc = "doc"
        const val propertys = "properties"

        val arbeidsledigeHeaderProps = arrayOf(periode, fylkesnummer, komunenummer, yrkeskode, arbeidledige, yrkesgruppe_lvl_1, yrkesgruppe_lvl_2)
        val ledigeStillingHeaderProps = arrayOf(periode, fylkesnummer, komunenummer, yrkeskode, ledigeStillinger, yrkesgruppe_lvl_1, yrkesgruppe_lvl_2)

        val indexMap = mapOf(arbeidsledigeIndex to arbeidsledigeHeaderProps, stillingerIndex to ledigeStillingHeaderProps)
        val filHeaderMap = mapOf(arbeidsledigeIndex to arbeidsledigeHeader, stillingerIndex to stillingerHeader)
    }
}
