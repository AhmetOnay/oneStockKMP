package screens.screener

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import widgets.CustomScaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.lifecycle.viewmodel.compose.viewModel
import navigation.NavManager
import screens.screener.tabs.StockScreenerListTab
import screens.screener.tabs.StockSearchTab
import viewmodels.*


@Composable
fun StockScreenerScreen(navManager: NavManager, viewModel: StockScreenerViewModel) {
    val tabs = listOf("Search", "List")
    var selectedTabIndex by remember { mutableStateOf(0) }
    val searchCompleted by viewModel.searchCompleted
    LaunchedEffect(searchCompleted) {
        if (searchCompleted) {
            selectedTabIndex = 1
            viewModel.searchCompleted.value = false
        }
    }
    CustomScaffold(
        navManager = navManager,
        topBarTitle = "Stock Screener",
        overflowMenuContent = if (selectedTabIndex == 1) {
            { OverflowMenu(viewModel) }
        } else null
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
                0 -> StockSearchTab(navManager, viewModel)
                1 -> StockScreenerListTab(navManager = navManager, stockScreenerViewModel = viewModel)
            }
        }
    }
}

@Composable
fun OverflowMenu(viewModel: StockScreenerViewModel) {
    var showMenu by remember { mutableStateOf(false) }

    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(Icons.Default.MoreVert, contentDescription = "More")
    }

    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        DropdownMenuItem(onClick = {
            showMenu = false
            viewModel.sortAscendingByMarketCap()
        }) {
            Text("Sort asc")
        }
        DropdownMenuItem(onClick = {
            viewModel.sortDescendingByMarketCap()
            showMenu = false
        }) {
            Text("Sort desc")
        }
        DropdownMenuItem(onClick = {
            viewModel.sortStocksByHighestMarketCap()
            showMenu = false
        }) {
            Text("Sort by highest Marketcap")
        }
    }
}