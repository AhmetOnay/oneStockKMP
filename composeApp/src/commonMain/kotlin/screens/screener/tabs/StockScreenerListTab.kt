package screens.screener.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import models.Screener
import navigation.NavManager
import navigation.Screens
import viewmodels.StockScreenerViewModel


@Composable
fun StockScreenerListTab(
    navManager: NavManager,
    stockScreenerViewModel: StockScreenerViewModel
) {
    val stocksList by stockScreenerViewModel.stockScreenerSearchData.collectAsState(initial = emptyList())

    LazyColumn {
        items(stocksList) { stock ->
            StockScreenerItem(stock) {
                navManager.navigate(Screens.StockDetail.createRoute(stock.symbol))
            }
        }
    }
}

@Composable
fun StockScreenerItem(stock: Screener, onItemClick: () -> Unit) {
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
            Text(text = "Name: ${stock.companyName}", style = MaterialTheme.typography.h6)
            Text(
                text = "Symbol: ${stock.symbol}",
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}
