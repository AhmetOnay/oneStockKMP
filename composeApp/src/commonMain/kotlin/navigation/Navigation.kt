package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import viewmodels.*
import api.ApiService
import api.NetworkClient
import repositories.DataRepository
import repositories.StockNewsRepository
import repositories.StockRepository
import screens.details.StockDetailScreen
import screens.home.HomeScreen
import screens.news.StockNewsScreen
import screens.screener.StockScreenerScreen
import screens.zakat.ZakatScreen


@Composable
fun SetupNavGraph(navManager: NavManager) {
    val currentRoute = navManager.currentRoute.value
    val apiService = ApiService(NetworkClient.httpClient)
    val dataRepository = DataRepository(apiService)
    val stockRepository = StockRepository(apiService)
    val stockViewModel: StockViewModel = StockViewModel(dataRepository, stockRepository)
    val stockNewsRepository = StockNewsRepository(apiService)
    val stockNewsViewModel: StockNewsViewModel = StockNewsViewModel(stockNewsRepository)
    val zakatViewModel: ZakatViewModel = ZakatViewModel(dataRepository)
    val stockScreenerViewModel: StockScreenerViewModel = StockScreenerViewModel(dataRepository)
    when {
        currentRoute.startsWith(Screens.Home.route) -> HomeScreen(navManager, stockViewModel)
        currentRoute.contains("stockDetail") -> {
            val symbol = currentRoute.substringAfter("stockDetail/")
            val stockDetailViewModel = StockDetailViewModel(dataRepository, stockRepository, symbol)
            StockDetailScreen(navManager, stockDetailViewModel, symbol)
        }
        currentRoute.startsWith(Screens.StockNews.route) -> StockNewsScreen(navManager, stockNewsViewModel)
        currentRoute.startsWith(Screens.Zakat.route) -> ZakatScreen(navManager, zakatViewModel)
        currentRoute.startsWith(Screens.StockScreener.route) -> StockScreenerScreen(navManager, stockScreenerViewModel)
    }
}

class NavManager {
    var currentRoute: MutableState<String> = mutableStateOf(Screens.Home.route)
    private val routeStack = mutableListOf(Screens.Home.route)
    fun navigate(route: String) {
        routeStack.add(route)
        currentRoute.value = route
    }

    fun navigateUp() {
        if (routeStack.isNotEmpty()) {
            routeStack.removeLast()
            currentRoute.value = routeStack.lastOrNull() ?: Screens.Home.route
        }
    }

    fun canGoBack(): Boolean = routeStack.size > 1
}