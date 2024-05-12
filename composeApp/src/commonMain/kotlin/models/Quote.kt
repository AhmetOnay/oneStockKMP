package models

import kotlinx.serialization.Serializable


@Serializable
data class Quote(
    val symbol: String,
    val name: String,
    val price: Double? = null,
    val changesPercentage: Double? = null,
    val change: Double,
    val dayLow: Double? = null,
    val dayHigh: Double? = null,
    val yearHigh: Double? = null,
    val yearLow: Double? = null,
    val marketCap: Long? = null,
    val priceAvg50: Double? = null,
    val priceAvg200: Double? = null,
    val exchange: String? = null,
    val volume: Long? = null,
    val avgVolume: Long? = null,
    val open: Double? = null,
    val previousClose: Double? = null,
    val eps: Double? = null,
    val pe: Double? = null,
    val earningsAnnouncement: String? = null,
    val sharesOutstanding: Long? = null,
    val timestamp: Long? = null
)