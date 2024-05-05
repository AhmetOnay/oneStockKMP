import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import navigation.NavManager
import navigation.SetupNavGraph
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

import ui.theme.OneStockTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    val isDarkTheme = false
    val navManager = remember { NavManager() }
    OneStockTheme(darkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            SetupNavGraph(navManager)
        }
    }
}