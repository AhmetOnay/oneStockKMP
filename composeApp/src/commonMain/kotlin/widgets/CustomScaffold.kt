package widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.Screens
import kotlinx.coroutines.launch
import navigation.NavManager

@Composable
fun CustomScaffold(
    navManager: NavManager,
    topBarTitle: String = "One Stock",
    drawerHeader: @Composable () -> Unit = { DrawerHeader() },
    drawerBody: @Composable () -> Unit = { DrawerBody(navManager) },
    overflowMenuContent: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topBarTitle) },
                backgroundColor = MaterialTheme.colors.primary,
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            //scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    overflowMenuContent?.invoke()
                }
            )
        },
        drawerContent = {
            drawerHeader()
            drawerBody()
        },
        content = content
    )
}

@Composable
fun DrawerHeader() {
    Text("One Stock", style = MaterialTheme.typography.h6, modifier = Modifier.padding(16.dp))
}

@Composable
fun DrawerBody(navManager: NavManager) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { navManager.navigate(Screens.Home.route) }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Home")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { navManager.navigate(Screens.StockNews.route) }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("News")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { navManager.navigate(Screens.StockScreener.route) }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Stock Screener")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { navManager.navigate(Screens.Zakat.route) }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Zakat Calculator")
    }
}
