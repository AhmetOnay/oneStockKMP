package screens.home.tabs
/*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import models.Quote
import navigation.Screens
import org.example.onestock.repositories.StockRepository
import org.example.onestock.viewmodels.StockViewModel


@Composable
fun WatchlistTab(navController: NavHostController, stockViewModel: StockViewModel) {
    val savedStocksList by stockViewModel.savedStocksQuotesLiveData.observeAsState(initial = emptyList())

    LazyColumn {
        items(savedStocksList) { stock ->
            WatchlistItem(stock) {
                navController.navigate(Screens.StockDetail.createRoute(stock.symbol))
            }
        }
    }
}

@Composable
fun WatchlistItem(stock: Quote, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onItemClick),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Name: ${stock.name}", style = MaterialTheme.typography.h6)
            Text(
                text = "Symbol: ${stock.symbol}",
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}
*/