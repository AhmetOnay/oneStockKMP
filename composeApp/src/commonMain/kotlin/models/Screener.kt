package models

import kotlinx.serialization.Serializable

@Serializable
data class Screener(
    val symbol: String,
    val companyName: String? = "",
    val marketCap: Long? = null,
    val sector: String? = "",
    val industry: String? = "",
    val beta: Double? = 0.0,
    val price: Double? = 0.0,
    val lastAnnualDividend: Double? = null,
    val volume: Long? = 0,
    val exchange: String? = null,
    val exchangeShortName: String? = "",
    val country: String? = "",
    val isEtf: Boolean? = null,
    val isActivelyTrading: Boolean? = null
)
