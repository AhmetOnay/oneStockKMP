package repositories

import data.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import models.*


class StockNewsRepository(private val apiService: ApiService) {
    val apiKey = "ywiRvhvopmrvF3rbsOMZMsyVbnovP10OjJhqZlbg"
    
    private val _newsData = MutableStateFlow<News?>(null)
    val newsData: StateFlow<News?> = _newsData

    suspend fun fetchNews(countries: String, filterEntities: Boolean, limit: Int, publishedAfter: String) {
        try {
            val news = apiService.marketauxApi.getNews(countries, filterEntities, limit, publishedAfter, apiKey)
            _newsData.value = news
        } catch (e: Exception) {
            println("Error fetching news: $e")
            _newsData.value = null
        }
    }

    suspend fun fetchNewsSynchronously(countries: String, filterEntities: Boolean, limit: Int, publishedAfter: String): News? {
        return try {
            apiService.marketauxApi.getNews(countries, filterEntities, limit, publishedAfter, apiKey)
        } catch (e: Exception) {
            println("Error fetching news synchronously: $e")
            null
        }
    }

}