package com.example.khabar

import DataStorageManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ReportsScreen(navController: NavController, context: Context) {
    val reportSections = listOf(
        "Sales Report",
        "Transaction History",
        "Deposit History",
        // "Incentive History",
        "My Hierarchy"
    )
    var mNumber: String? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        DataStorageManager.getMobileNumber(context).collect { number ->
            mNumber = number
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray.copy(0.2f))
                    .padding(24.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_profile),
                    contentDescription = "Profile Photo",
                    modifier = Modifier.size(96.dp),
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
                Spacer(Modifier.width(8.dp))

                Column {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Distributor 1",
                        fontWeight = FontWeight.Bold,
                        lineHeight = 16.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = mNumber.orEmpty(),
                        fontWeight = FontWeight.Normal,
                        lineHeight = 16.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "DIST1",
                        fontWeight = FontWeight.Normal,
                        lineHeight = 16.sp,
                        style = MaterialTheme.typography.bodySmall
                    )

                }

            }
        }
        items(reportSections) { section ->
            ReportSectionItem(section) {
                when (section) {
                    "Sales Report" -> {
                        navController.navigate("sales_report")
                    }

                    "Transaction History" -> {
                        navController.navigate("transaction_history")
                    }

                    "Deposit History" -> {
                        navController.navigate("deposit_history")

                    }

                    "Incentive History" -> {
                        // todo
                    }

                    "My Hierarchy" -> {
                        Toast.makeText(context, " Work in Progress !!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun ReportSectionItem(section: String, onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable {
                onClick(section)
            }
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = section,
            lineHeight = 48.sp,
            style = MaterialTheme.typography.titleSmall
        )

    }
}
