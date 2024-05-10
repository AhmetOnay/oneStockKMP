package viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

import models.*
import repositories.*

class StockNewsViewModel(private val stockNewsRepository: StockNewsRepository) : ViewModel() {
    val stockNewsData: StateFlow<News?> = stockNewsRepository.newsData

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            val todayDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            val formattedDateTime = "${todayDate}T00:00:00"
            stockNewsRepository.fetchNews("us", true, 3, formattedDateTime)
        }
    }
}