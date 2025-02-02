package com.example.khabar

import CustomClickableRow
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import showDatePicker

@Composable
fun TransactionHistoryScreen(navController: NavController) {
    var fromDate by remember { mutableStateOf("") }
    var toDate by remember { mutableStateOf("") }
    var showTransactionDialog by remember { mutableStateOf(false) }
    val transactionOptions =
        listOf("All", "Voucher Purchase", "Transfer", "Prepaid Topup", "Postpaid Topup")
    var selectedTransaction by remember { mutableStateOf(transactionOptions.first()) }
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .background(
                    Color(0xFFFFD700),
                    RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 32.dp,
                        horizontal = 16.dp
                    )
                    .align(Alignment.TopStart),
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
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_tran_history),
                    contentDescription = "bar_chart",
                    colorFilter = ColorFilter.tint(color = Color.Black)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(modifier = Modifier.padding(16.dp)) {

            CustomClickableRow("From Date", fromDate) { showDatePicker(context) { fromDate = it } }
            CustomClickableRow("To Date", toDate) { showDatePicker(context) { toDate = it } }
            CustomClickableRow("Transaction Type", selectedTransaction) {
                showTransactionDialog = true
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (fromDate.isEmpty() || toDate.isEmpty()) {
                        Toast.makeText(context,"Date Can't be empty", Toast.LENGTH_SHORT).show()
                    } else {
                        navController.navigate("transaction_detail")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
            ) {
                Text("Submit", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }
    }

    if (showTransactionDialog) {
        AlertDialog(
            onDismissRequest = { showTransactionDialog = false },
            title = { Text("Select Transaction Type") },
            text = {
                Column {
                    transactionOptions.forEach { option ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedTransaction = option
                            }
                            .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = option, fontSize = 16.sp, modifier = Modifier.weight(1f))
                            if (selectedTransaction == option) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color(0xFFFFD700)
                                )
                            }
                        }
                    }
                }
            },
            containerColor = Color.White,
            confirmButton = {
                TextButton(onClick = { showTransactionDialog = false }) {
                    Text("Done", color = Color(0xFFFFD700))
                }
            },
            dismissButton = {
                TextButton(onClick = { showTransactionDialog = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            }
        )
    }
}
