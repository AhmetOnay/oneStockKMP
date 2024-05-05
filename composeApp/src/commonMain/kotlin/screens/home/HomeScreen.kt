package screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.NavManager
import widgets.CustomScaffold
import kotlinx.coroutines.launch
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*


@Composable
fun HomeScreen(navManager: NavManager) {
    val tabs = listOf("Watchlist", "Trending", "Search")
    var selectedTabIndex by remember { mutableStateOf(1) }
    //stockViewModel.getStockData("AAPL", "1min")

    //val stockData by stockViewModel.stockData.observeAsState()
    val scope = rememberCoroutineScope()
    var text by remember { mutableStateOf("Loading") }
    scope.launch {
        text = try {
            greeting()
        } catch (e: Exception) {
            "error"
        }
    }
    println(text);
    CustomScaffold(
        navManager =  navManager,
        topBarTitle = "One Stock",
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                backgroundColor = MaterialTheme.colors.secondary,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = MaterialTheme.colors.onPrimary,
                        height = 3.dp
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title, color = MaterialTheme.colors.onPrimary) },
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }

            /*when (selectedTabIndex) {
                0 -> WatchlistTab(navController = navController, stockViewModel = stockViewModel)
                1 -> MostActiveTab(navController, stockViewModel)
                2 -> StockSearchTab(navController, stockViewModel)
            }*/
        }
    }
}


@Composable
fun StockList(category: String, txt: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("List of $category", style = MaterialTheme.typography.h6)
        Text("$txt", style = MaterialTheme.typography.h6)
    }
}

private val client = HttpClient()
suspend fun greeting(): String {
    val response = client.get("https://ktor.io/docs/")
    return response.bodyAsText()
}


