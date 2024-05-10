package data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import models.*

class ApiService(private val client: HttpClient) {
    private val fmpBaseUrl = "https://financialmodelingprep.com/api/v3/"
    private val marketauxBaseUrl = "https://api.marketaux.com/v1/"

    inner class FMPApi {

        suspend fun getTimeSeries(symbol: String, interval: String, apiKey: String): TimeSeries? {
            val response: HttpResponse = client.get("$fmpBaseUrl/time_series") {
                parameter("symbol", symbol)
                parameter("interval", interval)
                parameter("apikey", apiKey)
            }
            return response.body()
        }

        suspend fun getStocksList(apiKey: String): List<StockInfo>? {
            val response: HttpResponse = client.get("$fmpBaseUrl/stock/list") {
                parameter("apikey", apiKey)
            }
            return if (response.status.isSuccess()) {
                response.body()
            } else {
                null
            }
        }

        suspend fun getMostActive(apiKey: String): List<Quote>? {
            try {
                val response: HttpResponse = client.get("$fmpBaseUrl/stock_market/actives") {
                    parameter("apikey", apiKey)
                }
                if (response.status == HttpStatusCode.OK) {
                    val responseBody = response.bodyAsText()
                    return Json.decodeFromString<List<Quote>>(responseBody)
                } else {
                    println("HTTP Request failed with status: ${response.status}")
                }
            } catch (e: Exception) {
                println("Exception occurred: $e")
            }
            return null
        }


        suspend fun getGeneralSearch(query: String, apiKey: String): List<StockInfo>? {
            try {
                val response: HttpResponse = client.get("$fmpBaseUrl/search") {
                    parameter("query", query)
                    parameter("apikey", apiKey)
                }
                if (response.status == HttpStatusCode.OK) {
                    val responseBody = response.bodyAsText()
                    return Json.decodeFromString<List<StockInfo>>(responseBody)
                } else {
                    println("HTTP Request failed with status: ${response.status}")
                }
            } catch (e: Exception) {
                println("Exception occurred: $e")
            }
            return null
        }

        suspend fun getQuote(symbol: String, apiKey: String): List<Quote>? {
            val response: HttpResponse = client.get("$fmpBaseUrl/quote/$symbol") {
                parameter("apikey", apiKey)
            }
            return if (response.status.isSuccess()) {
                response.body()
            } else {
                null
            }
        }

        suspend fun getQuote2(symbol: String, apiKey: String): List<Quote>? {
            val response: HttpResponse = client.get("$fmpBaseUrl/quote/$symbol") {
                parameter("apikey", apiKey)
            }
            return if (response.status == HttpStatusCode.OK) {
                try {
                    response.body<List<Quote>>()
                } catch (e: Exception) {
                    println("Failed to parse response: ${e}")
                    null
                }
            } else {
                println("HTTP Request failed with status: ${response.status}")
                null
            }
        }

        suspend fun getBalanceSheet(symbol: String, apiKey: String): List<BalanceSheet>? {
            val response: HttpResponse = client.get("$fmpBaseUrl/balance-sheet-statement/$symbol") {
                parameter("period", "annual")
                parameter("apikey", apiKey)
            }
            return if (response.status.isSuccess()) {
                response.body()
            } else {
                null
            }
        }

        suspend fun getScreener(
            apiKey: String,
            marketCapMoreThan: Long? = null,
            marketCapLowerThan: Long? = null,
            priceMoreThan: Double? = null,
            priceLowerThan: Double? = null,
            betaMoreThan: Double? = null,
            betaLowerThan: Double? = null,
            volumeMoreThan: Long? = null,
            volumeLowerThan: Long? = null,
            dividendMoreThan: Double? = null,
            dividendLowerThan: Double? = null,
            isEtf: Boolean? = null,
            isActivelyTrading: Boolean? = null,
            sector: String? = null,
            industry: String? = null,
            country: String? = null,
            exchange: String? = null,
            limit: Int? = null
        ): List<Screener>? {
            val response: HttpResponse = client.get("$fmpBaseUrl/stock-screener") {
                parameter("apikey", apiKey)
                marketCapMoreThan?.let { parameter("marketCapMoreThan", it) }
                marketCapLowerThan?.let { parameter("marketCapLowerThan", it) }
                priceMoreThan?.let { parameter("priceMoreThan", it) }
                priceLowerThan?.let { parameter("priceLowerThan", it) }
                betaMoreThan?.let { parameter("betaMoreThan", it) }
                betaLowerThan?.let { parameter("betaLowerThan", it) }
                volumeMoreThan?.let { parameter("volumeMoreThan", it) }
                volumeLowerThan?.let { parameter("volumeLowerThan", it) }
                dividendMoreThan?.let { parameter("dividendMoreThan", it) }
                dividendLowerThan?.let { parameter("dividendLowerThan", it) }
                isEtf?.let { parameter("isEtf", it) }
                isActivelyTrading?.let { parameter("isActivelyTrading", it) }
                sector?.let { parameter("sector", it) }
                industry?.let { parameter("industry", it) }
                country?.let { parameter("country", it) }
                exchange?.let { parameter("exchange", it) }
                limit?.let { parameter("limit", it) }
            }
            return if (response.status.isSuccess()) {
                response.body()
            } else {
                null
            }
        }
    }

    inner class MarketauxApi {
        suspend fun getNews(
            countries: String,
            filterEntities: Boolean,
            limit: Int,
            publishedAfter: String,
            apiToken: String
        ): News? {
            val response: HttpResponse = client.get("$marketauxBaseUrl/news/all") {
                parameter("countries", countries)
                parameter("filter_entities", filterEntities)
                parameter("limit", limit)
                parameter("published_after", publishedAfter)
                parameter("api_token", apiToken)
            }
            return if (response.status.isSuccess()) {
                response.body()
            } else {
                null
            }
        }
    }

    val fmpApi = FMPApi()
    val marketauxApi = MarketauxApi()
}
