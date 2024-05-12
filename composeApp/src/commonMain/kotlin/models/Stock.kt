package models

import kotlinx.serialization.Serializable

@Serializable
data class StockResponse(
    val results: List<Stock>
)

@Serializable
data class Stock(
    val objectId: String? = null,
    val symbol: String = "",
    val name: String = "",
    val note: String = ""
)

