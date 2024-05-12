package viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import models.Screener
import repositories.DataRepository
import kotlinx.coroutines.launch

class StockScreenerViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _stockScreenerSearchData = MutableStateFlow<List<Screener>>(emptyList())
    var stockScreenerSearchData: StateFlow<List<Screener>> = _stockScreenerSearchData
    var searchCompleted = mutableStateOf(false)

    init {
        viewModelScope.launch {
            dataRepository.stockScreenerSearchData.collect {
                _stockScreenerSearchData.value = it ?: emptyList()
            }
        }
    }

    fun search(
        country: String?,
        industry: String?,
        marketCapMoreThan: Long?,
        marketCapLowerThan: Long?,
        dividendMoreThan: Double?,
        dividendLowerThan: Double?,
        volumeMoreThan: Long?,
        volumeLowerThan: Long?

    ) {
        viewModelScope.launch {
            dataRepository.fetchScreener(
                country,
                industry,
                marketCapMoreThan,
                marketCapLowerThan,
                dividendMoreThan,
                dividendLowerThan,
                volumeMoreThan,
                volumeLowerThan
            )
            searchCompleted.value = true
        }
    }

    fun sortAscendingByMarketCap() {
        val sortedList = stockScreenerSearchData.value?.sortedBy { it.companyName }
        if (sortedList != null) {
            _stockScreenerSearchData.value = sortedList
        }
    }

    fun sortDescendingByMarketCap() {
        val sortedList = stockScreenerSearchData.value?.sortedByDescending { it.companyName }
        if (sortedList != null) {
            _stockScreenerSearchData.value = sortedList
        }
    }

    fun sortStocksByHighestMarketCap() {
        val sortedList = _stockScreenerSearchData.value?.sortedByDescending { it.marketCap }
        if (sortedList != null) {
            _stockScreenerSearchData.value = sortedList
        }
    }
}