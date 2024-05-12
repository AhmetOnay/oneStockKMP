package screens.details.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.*
import widgets.*

@Composable
fun DashboardTab(balanceSheetData: BalanceSheet?, quoteData: Quote?, innerPadding: PaddingValues) {
    Column(modifier = androidx.compose.ui.Modifier.padding(innerPadding)) {
        Column(modifier = Modifier.padding(16.dp)) {
            quoteData?.let { quote ->
                QuoteWidget(quote = quote)
                Divider(modifier = Modifier.padding(vertical = 16.dp))
            }
            balanceSheetData?.let { balanceSheet ->
                BalanceSheetWidget(balanceSheet = balanceSheet)
            }
        }
    }
}