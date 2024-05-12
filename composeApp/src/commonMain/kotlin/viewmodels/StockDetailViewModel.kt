package viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.NoteSerialization

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import models.*
import repositories.DataRepository
import repositories.StockRepository

class StockDetailViewModel(
    private val dataRepository: DataRepository,
    private val stockRepository: StockRepository, private val symbol: String = ""
) : ViewModel() {
    val balanceSheetData: StateFlow<BalanceSheet?> = dataRepository.balanceSheetData
    val quoteData: StateFlow<Quote?> = dataRepository.quoteData
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    var stock by mutableStateOf(Stock())
        private set

    init {
        viewModelScope.launch {
            loadStockAndNotes()
        }
    }

    private suspend fun loadStockAndNotes() {
        if (symbol.isNotEmpty()) {
            val stock = stockRepository.getStockBySymbol(symbol)
            this.stock = stock ?: Stock()
            _notes.value = NoteSerialization.deserializeNotes(stock?.note ?: "")
        }
    }

    suspend fun getBalanceSheetInfo(txt: String) {
        dataRepository.fetchBalanceSheet(txt)
    }

    suspend fun getQuoteInfo(txt: String) {
        return dataRepository.fetchQuote(txt)
    }

    suspend fun saveStock(symbol: String) {
        var newStock = Stock(symbol = symbol)
        try {
            val createdStock = stockRepository.addStock(newStock)
            stock = createdStock
        } catch (e: Exception) {
            println("Failed to add stock: ${e.message}")
        }
    }

    private suspend fun updateStock(stock: Stock) {
        stockRepository.updateStock(stock);
        loadStockAndNotes()
    }

    suspend fun addNewNote(text: String) {
        val currentNotes = try {
            NoteSerialization.deserializeNotes(stock.note)
        } catch (e: Exception) {
            emptyList<Note>()
        }
        val nextId = (currentNotes.maxByOrNull { it.id }?.id ?: 0L) + 1
        val newNote = Note(id = nextId, text = text, timestamp = Clock.System.now().toEpochMilliseconds())
        val updatedNotes = currentNotes + newNote
        val updatedNotesJson = NoteSerialization.serializeNotes(updatedNotes)
        val updatedStock = stock.copy(note = updatedNotesJson)
        updateStock(updatedStock)
    }

    suspend fun deleteNote(noteId: Long) {
        val currentNotes = try {
            NoteSerialization.deserializeNotes(stock.note)
        } catch (e: Exception) {
            emptyList<Note>()
        }
        val updatedNotes = currentNotes.filter { it.id != noteId }
        val updatedNotesJson = NoteSerialization.serializeNotes(updatedNotes)
        val updatedStock = stock.copy(note = updatedNotesJson)
        updateStock(updatedStock)
    }

    suspend fun deleteStock() {
        stockRepository.deleteStock(stock)
        loadStockAndNotes()
    }
}