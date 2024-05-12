package repositories

import api.ApiService
import models.Stock

class StockRepository(private val apiService: ApiService) {

    suspend fun addStock(stock: Stock): Stock {
        try {
            return apiService.back4AppApi.addStock(stock)
        } catch (e: Exception) {
            println("Failed to add stock: ${e.message}")
            throw e
        }
    }

    suspend fun deleteStock(stock: Stock){
        try {
            apiService.back4AppApi.deleteStock(stock)
        } catch (e: Exception) {
            println("Failed to delete stock: ${e.message}")
        }
    }

    suspend fun updateStock(stock: Stock){
        try {
            apiService.back4AppApi.updateStock(stock)
        } catch (e: Exception) {
            println("Failed to update stock: ${e.message}")
        }
    }

    suspend fun getAllStocks(): List<Stock> {
        return try {
            apiService.back4AppApi.getAllStocks()
        } catch (e: Exception) {
            println("Failed to retrieve stocks: ${e.message}")
            emptyList()
        }
    }

    suspend fun getAllStockSymbols(): List<String> {
        return try {
            val stocks = getAllStocks()
            stocks.map { it.symbol } 
        } catch (e: Exception) {
            println("Failed to get allStockSymbols: ${e.message}")
            emptyList()
        }
    }
    
    suspend fun getStockBySymbol(symbol : String): Stock? {
            return getAllStocks().find { it.symbol == symbol }
    }
    
    suspend fun getStockByID(id: String): Stock? {
        return try {
            apiService.back4AppApi.getStockByID(id)
        } catch (e: Exception) {
            println("Failed to retrieve stock: ${e.message}")
            null
        }
    }
}