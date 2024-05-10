package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import viewmodels.*
import data.ApiService
import data.NetworkClient
import repositories.DataRepository
import repositories.StockNewsRepository
import screens.home.HomeScreen
import screens.news.StockNewsScreen


@Composable
fun SetupNavGraph(navManager: NavManager) {
    val currentRoute = navManager.currentRoute.value
    val apiService = ApiService(NetworkClient.httpClient)
    val dataRepository = DataRepository(apiService)
    val stockViewModel: StockViewModel = StockViewModel(dataRepository)
    val stockNewsRepository = StockNewsRepository(apiService)
    val stockNewsViewModel: StockNewsViewModel = StockNewsViewModel(stockNewsRepository)
    when {
        currentRoute.startsWith(Screens.Home.route) -> HomeScreen(navManager, stockViewModel)
       /* currentRoute.startsWith(Screens.StockDetail.route) -> {
            // Extract the symbol from the route
            val symbol = currentRoute.substringAfter("stockDetail/")
            StockDetailScreen(navManager, stockViewModel, symbol)
        }*/
        currentRoute.startsWith(Screens.StockNews.route) -> StockNewsScreen(navManager,stockNewsViewModel)
        //currentRoute.startsWith(Screens.Zakat.route) -> ZakatScreen(navManager)
        //currentRoute.startsWith(Screens.StockScreener.route) -> StockScreenerScreen(navManager)

    } 
}

class NavManager {
    var currentRoute: MutableState<String> = mutableStateOf(Screens.Home.route)
    fun navigate(route: String) {
        currentRoute.value = route
    }
}