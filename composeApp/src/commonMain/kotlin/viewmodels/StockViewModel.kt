package viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import models.*
import repositories.*

class StockViewModel(private val dataRepository: DataRepository, private val stockRepository: StockRepository) :
    ViewModel() {

    val stockData: StateFlow<TimeSeries?> = dataRepository.timeSeriesData
    val savedStocksQuotesLiveData = MutableStateFlow<List<Quote>>(emptyList())
    val mostActiveData: StateFlow<List<Quote>> = dataRepository.mostActiveData
    val generalSearchData: StateFlow<List<StockInfo>?> = dataRepository.generalSearchData

    private val _symbols = MutableStateFlow<List<String>>(emptyList())
    val symbols: StateFlow<List<String>> = _symbols

    init {
        //getMostActive()
        viewModelScope.launch {
            _symbols.value = stockRepository.getAllStockSymbols()
            getSavedStocksQuotesLiveData()
        }
        //getStocksList()
    }

    fun getStockData(symbols: String, interval: String) {
        viewModelScope.launch {
            dataRepository.fetchTimeSeries(symbols, interval)
        }
    }

    private fun getStocksList() {
        viewModelScope.launch {
            dataRepository.fetchStocksList()
        }
    }

    private fun getSavedStocksQuotesLiveData() {
        viewModelScope.launch {
            val quotes = mutableListOf<Quote>()
            _symbols.value.forEach { symbol ->
                dataRepository.fetchQuote2(symbol)?.let { quotes.add(it) }
            }
            savedStocksQuotesLiveData.value = quotes
        }
    }

    private fun getMostActive() {
        viewModelScope.launch {
            dataRepository.fetchMostActive()
        }
    }

    fun generalSearch(txt: String) {
        if (txt.isNotBlank()) {
            viewModelScope.launch {
                dataRepository.fetchGeneralSearch(txt)
            }
        }
    }
}

