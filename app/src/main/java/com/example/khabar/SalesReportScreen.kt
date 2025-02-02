import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Scaffold

import androidx.navigation.NavController
import com.example.khabar.R
import java.util.Calendar

@Composable
fun SalesReportScreen(navController: NavController) {
    var fromDate by remember { mutableStateOf("") }
    var toDate by remember { mutableStateOf("") }
    var selectedGraph by remember { mutableStateOf("Transaction Amount") }
    var showTransactionDialog by remember { mutableStateOf(false) }
    val transactionOptions =
        listOf("All", "Voucher Purchase", "Transfer", "Prepaid Topup", "Postpaid Topup")
    var selectedTransaction by remember { mutableStateOf(transactionOptions.first()) }
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

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
                    text = "Sales Report",
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
                    painter = painterResource(R.drawable.ic_bar_chart),
                    contentDescription = "bar_chart",
                    colorFilter = ColorFilter.tint(color = Color.Black)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(modifier = Modifier.padding(16.dp)) {

            CustomClickableRow("From Date", fromDate) { showDatePicker(context) { fromDate = it } }
            CustomClickableRow("To Date", toDate) { showDatePicker(context) { toDate = it } }
            CustomDropdownRow(
                "Plot Graph On",
                selectedGraph,
                listOf("Transaction Amount", "Transaction Count")
            ) { selectedGraph = it }
            CustomClickableRow("Transaction Type", selectedTransaction) {
                showTransactionDialog = true
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (fromDate.isEmpty() || toDate.isEmpty()) {
                        Toast.makeText(context,"Date Can't be empty", Toast.LENGTH_SHORT).show()
                    } else {
                        navController.navigate("sales_details")
                    }                },
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

@Composable
fun CustomClickableRow(label: String, value: String, onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(8.dp)) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, RoundedCornerShape(32.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = value.ifEmpty { "Select $label" },
                modifier = Modifier.padding(start = 16.dp),
                color = Color.Black
            )
        }
    }
}

@Composable
fun CustomDropdownRow(
    label: String,
    selectedValue: String,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { expanded = true }
        .padding(8.dp)) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White, RoundedCornerShape(32.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = selectedValue,
                modifier = Modifier.padding(start = 16.dp),
                color = Color.Black
            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    },
                    modifier = Modifier.background(Color.White)
                )
            }
        }
    }
}

 fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day -> onDateSelected("$day-${month + 1}-$year") },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.show()
}
