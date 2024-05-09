package screens.home.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import viewmodels.*
import models.Quote
import navigation.NavManager
import navigation.Screens


@Composable
fun MostActiveTab(navManager: NavManager, stockViewModel: StockViewModel) {

    val stocksList by stockViewModel.mostActiveData.collectAsState(initial = emptyList())

    if (stocksList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(stocksList) { stock ->
                MostActiveItem(stock) {
                    navManager.navigate(Screens.StockDetail.createRoute(stock.symbol))
                }
            }
        }
    }
}

@Composable
fun MostActiveItem(stock: Quote, onItemClick: () -> Unit) {
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
