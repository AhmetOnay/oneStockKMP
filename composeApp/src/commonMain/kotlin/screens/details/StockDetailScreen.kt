package screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screens.details.tabs.*
import screens.*
import kotlinx.coroutines.launch
import navigation.NavManager
import viewmodels.StockDetailViewModel

@Composable
fun StockDetailScreen(navManager: NavManager,stockDetailViewModel: StockDetailViewModel, symbol: String) {
    
    val viewModel = stockDetailViewModel
    val balanceSheetData by viewModel.balanceSheetData.collectAsState()
    val quoteData by viewModel.quoteData.collectAsState()

    val stock = viewModel.stock
    val isInWatchlist by remember(stock) { derivedStateOf { stock.symbol.isNotEmpty() } }

    var selectedTabIndex by remember { mutableStateOf(0) }
    var tabs = listOf("Dashboard")
    if (stock.symbol != "") {
        tabs = listOf("Dashboard", "Note")
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(symbol) {
        viewModel.getBalanceSheetInfo(symbol)
        viewModel.getQuoteInfo(symbol)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Stock Detail Screen") },
                navigationIcon = if (navManager.canGoBack()) {
                    {
                        IconButton(onClick = { navManager.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                } else {
                    null
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            if (!isInWatchlist) {
                                viewModel.saveStock(symbol)
                            } else {
                                viewModel.deleteStock()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = if (isInWatchlist) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (isInWatchlist) "Remove from Favorites" else "Add to Favorites"
                        )
                    }
                })
        }
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

            when (selectedTabIndex) {
                0 -> DashboardTab(balanceSheetData, quoteData, innerPadding)
                1 -> stock?.let { NoteTab(stockDetailViewModel = viewModel) }
            }
        }
    }
}
