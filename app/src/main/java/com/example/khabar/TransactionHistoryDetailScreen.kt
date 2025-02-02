package com.example.khabar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.khabar.ui.theme.YellowMellow


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun TransactionHistoryDetailScreen(navController: NavController) {
    val transactions = listOf(
        TransactionItem("$40.00 ", "OTHER", "276400010", "21-01-2020"),
        TransactionItem("$40.00 ", "OTHER", "276400010", "21-01-2020"),
        TransactionItem("$40.00 ", "OTHER", "276661010", "21-01-2020"),
        TransactionItem("$1,00.00 ", "OTHER", "276601010", "21-01-2020"),
        TransactionItem("$40.00 ", "OTHER", "276406010", "21-01-2020"),
        TransactionItem("$40.00 ", "OTHER", "276401010", "21-01-2020"),
        TransactionItem("$40.00 ", "OTHER", "276499010", "21-01-2020"),
        TransactionItem("$40.00 ", "OTHER", "276407710", "21-01-2020"),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = YellowMellow)
                .padding(
                    vertical = 32.dp,
                    horizontal = 16.dp
                ),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { navController.popBackStack() })
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Transaction History",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray.copy(0.2f))
                .padding(horizontal = 4.dp, vertical = 20.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Transaction history from 1-1-2020 to 20-1-2020",
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(transactions) { transaction ->
                TransactionItemRow(transaction)
            }
        }
    }

}

@Composable
fun TransactionItemRow(transaction: TransactionItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = transaction.amount + " - " + transaction.type,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (transaction.amount.contains("1,000")) Color.Green.copy(0.4f) else Color.Red.copy(
                        0.4f
                    )
                )
                Text(text = transaction.accountNumber, fontSize = 8.sp, color = Color.Gray)

            }
            Text(text = transaction.date, fontSize = 8.sp, color = Color.Gray)
        }
    }
}

data class TransactionItem(
    val amount: String,
    val type: String,
    val accountNumber: String,
    val date: String
)