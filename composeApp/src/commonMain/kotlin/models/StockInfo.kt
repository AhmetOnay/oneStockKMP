package models

import kotlinx.serialization.Serializable

data class StocksList(
    val data: List<StockInfo>
)

@Serializable
data class StockInfo(
    val symbol: String,
    val name: String? = null,
    val price: Double? = null,
    val exchange: String? = null,
    val exchangeShortName: String? = null,
    val type: String? = null,
    val currency: String? = null,
    val stockExchange: String? = null
)