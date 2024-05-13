import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import api.ApiService
import api.NetworkClient
import common.notificationWorker
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import repositories.StockNewsRepository



@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") { App() }

    val formattedDateTime = getFormattedDateTime()
    val scope = MainScope()
     scope.launch {
        val apiService = ApiService(NetworkClient.httpClient)
        val stockNewsRepository = StockNewsRepository(apiService)

        val news = stockNewsRepository.fetchNewsSynchronously("us", true, 3, formattedDateTime)
        news?.let {
            notificationWorker("Stock Market News", it.data.firstOrNull()?.title ?: "No title available")
        }
    }
}

// pad.Start: makes "1" to "01";
fun getFormattedDateTime(): String {
    val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    val year = now.year
    val month = now.monthNumber.toString().padStart(2, '0')
    val day = now.dayOfMonth.toString().padStart(2, '0')
    val formattedDateTime = year.toString() + "-" + month + "-" + day + "T00:00"
    return formattedDateTime
}

