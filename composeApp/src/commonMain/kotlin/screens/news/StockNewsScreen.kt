package screens.news

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import navigation.NavManager
import models.*
import viewmodels.StockNewsViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import common.openUrl

import widgets.CustomScaffold


@Composable
fun StockNewsScreen(navManager: NavManager, stockNewsViewModel: StockNewsViewModel) {
    val viewModel: StockNewsViewModel = stockNewsViewModel

    var selectedTabIndex by remember { mutableStateOf(0) }
    var tabs = listOf("News")
    val newsData by viewModel.stockNewsData.collectAsState()


    CustomScaffold(
        navManager = navManager,
        topBarTitle = "News",
    ) { innerPadding ->
        newsData?.let { news ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(news.data.size) { index ->
                    val newsArticle = news.data[index]
                    NewsArticleItem(article = newsArticle)
                }
            }
        } ?: Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Loading news...")
        }
    }
}

@Composable
fun NewsArticleItem(article: NewsArticle) {
    Card(modifier = Modifier.padding(8.dp).fillMaxWidth(), elevation = 4.dp) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = article.title, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.description, style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(8.dp))
            val annotatedLinkString = AnnotatedString.Builder("Read more").apply {
                addStyle(style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline).toSpanStyle(), start = 0, end = 8)
            }.toAnnotatedString()

            ClickableText(
                text = annotatedLinkString,
                onClick = {
                    openUrl(article.url)
                },
                style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)
            )
        }
    }
}