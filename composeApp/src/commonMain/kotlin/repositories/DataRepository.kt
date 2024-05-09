package repositories

import data.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import models.*

class DataRepository(private val apiService: ApiService) {
    private val _timeSeriesData = MutableStateFlow<TimeSeries?>(null)
    val timeSeriesData: StateFlow<TimeSeries?> = _timeSeriesData

    private val _stocksListData = MutableStateFlow<List<StockInfo>?>(null)
    val stocksListData: StateFlow<List<StockInfo>?> = _stocksListData

    private val _mostActiveData = MutableStateFlow<List<Quote>>(emptyList())
    val mostActiveData: StateFlow<List<Quote>> = _mostActiveData

    private val _balanceSheetData = MutableStateFlow<BalanceSheet?>(null)
    val balanceSheetData: StateFlow<BalanceSheet?> = _balanceSheetData

    private val _quoteData = MutableStateFlow<Quote?>(null)
    val quoteData: StateFlow<Quote?> = _quoteData

    private val _generalSearchData = MutableStateFlow<List<StockInfo>?>(null)
    val generalSearchData: StateFlow<List<StockInfo>?> = _generalSearchData

    private val _stockScreenerSearchData = MutableStateFlow<List<Screener>?>(null)
    val stockScreenerSearchData: StateFlow<List<Screener>?> = _stockScreenerSearchData

    private val apiKey = "5qrH2plVtrYf8zlY8RxQLo16b0xgaOau"

    suspend fun fetchTimeSeries(symbols: String, interval: String) {
        try {
            val result = apiService.fmpApi.getTimeSeries(symbols, interval, apiKey)
            _timeSeriesData.value = result
        } catch (e: Exception) {
            print("error: fetchTimeSeries")
        }
    }

    suspend fun fetchStocksList() {
        try {
            val result = apiService.fmpApi.getStocksList(apiKey)
            _stocksListData.value = result
        } catch (e: Exception) {
            print("error: fetchStocksList")
        }
    }

    suspend fun fetchMostActive() {
        try {
            val result = apiService.fmpApi.getMostActive(apiKey)
            if (result != null) {
                _mostActiveData.value = result
            }
        } catch (e: Exception) {
            print("error: fetchMostActive")
        }
    }

    suspend fun fetchGeneralSearch(query: String) {
        try {
            val result = apiService.fmpApi.getGeneralSearch(query, apiKey)
            _generalSearchData.value = result
        } catch (e: Exception) {
            print("error: fetchGeneralSearch")
        }
    }

    suspend fun fetchBalanceSheet(symbol: String) {
        try {
            val result = apiService.fmpApi.getBalanceSheet(symbol, apiKey)?.firstOrNull()
            _balanceSheetData.value = result
        } catch (e: Exception) {
            print("error: fetchBalanceSheet")
        }
    }

    suspend fun fetchQuote(symbol: String) {
        try {
            val quotes = apiService.fmpApi.getQuote(symbol, apiKey)
            _quoteData.value = quotes?.firstOrNull()
        } catch (e: Exception) {
            println("Error fetchQuote: $e")
            _quoteData.value = null  
        }
    }
    
    suspend fun fetchQuote2(symbol: String): Quote? {
        return try {
            val quotes = apiService.fmpApi.getQuote2(symbol, apiKey)
            if (quotes != null && quotes.isNotEmpty()) {
                quotes.firstOrNull()  // Return the first Quote if available
            } else {
                println("No quotes found or error in fetchQuote2")
                null  // Return null to indicate no data or an error occurred
            }
        } catch (e: Exception) {
            println("Exception in fetchQuote2: ${e}")
            null  // Return null on exception
        }
    }

    suspend fun fetchScreener(
        country: String?,
        industry: String?,
        marketCapMoreThan: Long?,
        marketCapLowerThan: Long?,
        dividendMoreThan: Double?,
        dividendLowerThan: Double?,
        volumeMoreThan: Long?,
        volumeLowerThan: Long?
    ) {
        try {
            val result = apiService.fmpApi.getScreener( marketCapMoreThan = marketCapMoreThan,
                                                        marketCapLowerThan = marketCapLowerThan,
                                                        priceMoreThan = null,
                                                        priceLowerThan = null,
                                                        betaMoreThan = null,
                                                        betaLowerThan = null,
                                                        volumeMoreThan = volumeMoreThan,
                                                        volumeLowerThan = volumeLowerThan,
                                                        dividendMoreThan = dividendMoreThan,
                                                        dividendLowerThan = dividendLowerThan,
                                                        isEtf = null,
                                                        isActivelyTrading = null,
                                                        sector = null,
                                                        industry = industry,
                                                        country = country,
                                                        exchange = null,
                                                        limit = null,
                                                        apiKey = apiKey)
            _stockScreenerSearchData.value = result
        } catch (e: Exception) {
            print("error: fetchScreener")
        }
    }
}



