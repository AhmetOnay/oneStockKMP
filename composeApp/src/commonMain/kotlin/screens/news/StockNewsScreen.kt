package screens.news

import androidx.compose.runtime.*
import navigation.NavManager
import org.example.onestock.models.NewsArticle

import widgets.CustomScaffold


@Composable
fun StockNewsScreen(navManager: NavManager) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var tabs = listOf("News")


    CustomScaffold(
        navManager = navManager,
        topBarTitle = "News",
    ) { innerPadding ->
        
    }
}

@Composable
fun NewsArticleItem(article: NewsArticle) {
    
    
}