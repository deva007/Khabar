package com.example.khabar

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.khabar.ui.theme.YellowMellow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, drawerState: DrawerState) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        drawerState.close() // Ensure the drawer is closed when the screen is composed
    }

    BackHandler {
        // Send app to background
        (context as? Activity)?.moveTaskToBack(true)
    }
    val coroutineScope = rememberCoroutineScope()
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    val tabs = listOf(R.drawable.ic_home_new, R.drawable.ic_double_head_arrow, R.drawable.ic_document_new)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { NavigationDrawerContent(navController) }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = YellowMellow),
                    title = { Text(getTabTitle(selectedTab)) },
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(Icons.Rounded.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            MainContent(navController, padding, selectedTab, tabs, context) { selectedTab = it }
        }
    }
}

@Composable
private fun MainContent(
    navController: NavController,
    padding: PaddingValues,
    selectedTab: Int,
    tabs: List<Int>,
    context: Context,
    onTabSelected: (Int) -> Unit
) {
    var selectedSubTab by rememberSaveable { mutableStateOf(0) }
    val transactionsTabs = listOf("TOPUP", "TRANSFER", "PURCHASE")

    Column(modifier = Modifier
        .padding(padding)
        .fillMaxSize()) {
        TabLayout(selectedTab, tabs, onTabSelected)

        if(selectedTab == 0) {
            DottedCard()
        }

        if (selectedTab == 1) { // Show sub-tabs for Transactions
            SubTabLayout(selectedSubTab, transactionsTabs) { selectedSubTab = it }
        }

        if( selectedTab == 2) {
            ReportsScreen(navController, context)
        }

        if (selectedTab == 1 && selectedSubTab == 0) { // TOPUP Screen
            TopupForm()
        }
        if (selectedTab == 1 && selectedSubTab == 1) {
            TransferForm()
        }
        if (selectedTab == 1 && selectedSubTab == 2) {
            PurchaseForm()
        }
    }
}

@Composable
fun DottedCard() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val cardHeight = screenHeight * 0.33f // 33% of screen height

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor =YellowMellow) // Background color
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // White Dots Background
            Canvas(modifier = Modifier.fillMaxSize()) {
                val dotSize = 30.dp.toPx()
                val spacing = 48.dp.toPx()

                for (x in 0..size.width.toInt() step spacing.toInt()) {
                    for (y in 0..size.height.toInt() step spacing.toInt()) {
                        drawCircle(
                            color = Color.White,
                            radius = dotSize / 2,
                            center = Offset(x.toFloat(), y.toFloat())
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentWidth()
                        .background(Color.Black),
                ) {
                    Text(
                        text = " Current Balance ",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = YellowMellow,
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$ 1000.00",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopupForm() {
    var showBottomSheet by remember { mutableStateOf(false) }

    var msisdn by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Prepaid") }
    var isTopupEnabled by remember { mutableStateOf(false) }
    var msisdnError by remember { mutableStateOf(" ") }
    var amountError by remember { mutableStateOf(" ") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Prepaid / Postpaid Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            listOf("Prepaid", "Postpaid").forEach { type ->
                Button(
                    onClick = { selectedType = type },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == type) YellowMellow else Color.LightGray
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = type,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input Fields
        Column {
            Text(
                text = "Customer Mobile Number",
                fontSize = 14.sp,
                fontWeight = FontWeight.Thin,
                lineHeight = 24.sp,
                color = Color.Black
            )
            OutlinedTextField(
                value = msisdn,
                onValueChange = {
                    msisdn = it
                    msisdnError = when {
                        msisdn.length < 10 || msisdn.length > 14 -> "Must be between 10 and 14 digits"
                        else -> ""
                    }
                    isTopupEnabled = msisdnError.isEmpty() && amountError.isEmpty()
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(32.dp),
                isError = msisdnError.isNotBlank()
            )

            if (msisdnError.isNotEmpty()) {
                Text(
                    text = msisdnError,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Topup Amount",
                fontSize = 14.sp,
                fontWeight = FontWeight.Thin,
                lineHeight = 24.sp,
                color = Color.Black
            )
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                    amountError = when {
                        amount.isEmpty() || amount.toDoubleOrNull() == null || amount.toDouble() <= 0 -> "Amount must be greater than 0"
                        else -> ""
                    }
                    isTopupEnabled = msisdnError.isEmpty() && amountError.isEmpty()
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(32.dp),
                isError = amountError.isNotBlank()
            )

            if (amountError.isNotEmpty()) {
                Text(
                    text = amountError,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Click to Topup Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(if (isTopupEnabled) YellowMellow else Color.Gray)
                .clickable(enabled = isTopupEnabled) {
                    if (isTopupEnabled) {
                        showBottomSheet = true
                        // Handle top-up logic here
                    }
                }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Click to TOP UP",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                SuccessBottomSheetContent(
                    title = "TOPUP SUCCESSFUL",
                    amount = amount,
                    number = msisdn,
                    onDismiss = { showBottomSheet = false }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransferForm() {
    var showBottomSheet by remember { mutableStateOf(false) }

    var msisdn by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("MSISDN") }
    var isTransferEnabled by remember { mutableStateOf(false) }
    var msisdnError by remember { mutableStateOf(" ") }
    var amountError by remember { mutableStateOf(" ") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Prepaid / Postpaid Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            listOf("MSISDN", "Reseller ID").forEach { type ->
                Button(
                    onClick = { selectedType = type },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == type) YellowMellow else Color.LightGray
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = type,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input Fields
        Column {
            Text(
                text = if (selectedType == "MSISDN") "Reseller MSISDN" else "Reseller ID",
                fontSize = 14.sp,
                fontWeight = FontWeight.Thin,
                lineHeight = 24.sp,
                color = Color.Black
            )
            OutlinedTextField(
                value = msisdn,
                onValueChange = {
                    msisdn = it
                    msisdnError = when {
                        msisdn.length < 10 || msisdn.length > 14 -> "Must be between 10 and 14 digits"
                        else -> ""
                    }
                    isTransferEnabled = msisdnError.isEmpty() && amountError.isEmpty()
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(32.dp),
                isError = msisdnError.isNotBlank()
            )

            if (msisdnError.isNotEmpty()) {
                Text(
                    text = msisdnError,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Transfer Amount",
                fontSize = 14.sp,
                fontWeight = FontWeight.Thin,
                lineHeight = 24.sp,
                color = Color.Black
            )
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                    amountError = when {
                        amount.isEmpty() || amount.toDoubleOrNull() == null || amount.toDouble() <= 0 -> "Amount must be greater than 0"
                        else -> ""
                    }
                    isTransferEnabled = msisdnError.isEmpty() && amountError.isEmpty()
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(32.dp),
                isError = amountError.isNotBlank()
            )

            if (amountError.isNotEmpty()) {
                Text(
                    text = amountError,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(if (isTransferEnabled) YellowMellow else Color.Gray)
                .clickable(enabled = isTransferEnabled) {
                    if (isTransferEnabled) {
                        showBottomSheet = true
                    }
                }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Click to Transfer",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                SuccessBottomSheetContent(
                    title = "TRANSFER SUCCESSFUL",
                    amount = amount,
                    number = msisdn,
                    onDismiss = { showBottomSheet = false }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PurchaseForm() {
    var showBottomSheet by remember { mutableStateOf(false) }
    var msisdn by remember { mutableStateOf("") }
    var selectedAmount by remember { mutableStateOf("") }
    var isTransferEnabled by remember { mutableStateOf(false) }
    var msisdnError by remember { mutableStateOf(" ") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Column {
            Text(
                text = "Reseller MSISDN/ID",
                fontSize = 14.sp,
                fontWeight = FontWeight.Thin,
                lineHeight = 24.sp,
                color = Color.Black
            )
            OutlinedTextField(
                value = msisdn,
                onValueChange = {
                    msisdn = it

                    // Validate MSISDN and set error message
                    msisdnError = when {
                        it.length < 10 || it.length > 14 -> "MSISDN must be between 10 and 14 digits"
                        else -> ""
                    }

                    isTransferEnabled =
                        msisdn.isNotEmpty() && msisdnError.isEmpty() && selectedAmount.isNotEmpty() // Enable if no error and amount selected

                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(32.dp),
                isError = msisdnError.isNotEmpty() // Set isError based on error message
            )

            if (msisdnError.isNotEmpty()) {
                Text(
                    text = msisdnError,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Voucher Amount :-",
                fontSize = 14.sp,
                fontWeight = FontWeight.Thin,
                lineHeight = 24.sp,
                color = Color.Black
            )

            SelectableAmountRow(
                onAmountSelected = {
                    selectedAmount = it
                    isTransferEnabled =
                        msisdnError.isEmpty() && it.isNotEmpty() // Enable if no error and amount selected
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(if (isTransferEnabled) YellowMellow else Color.Gray) // Conditional background
                .clickable(enabled = isTransferEnabled) {
                    if (isTransferEnabled) {
                        showBottomSheet = true
                    }
                }
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Click to Purchase",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                SuccessBottomSheetContent(
                    title = "PURCHASE SUCCESSFUL",
                    amount = selectedAmount,
                    number = msisdn,
                    onDismiss = { showBottomSheet = false }
                )
            }
        }

    }
}



@Composable
fun SelectableAmountRow(onAmountSelected: (String) -> Unit) {  // Callback for selection
    val amounts = listOf("100", "200", "500", "1000")
    var selectedAmount by remember { mutableStateOf("") }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(amounts.size) { index ->
            val amount = amounts[index]
            Button(
                onClick = {
                    selectedAmount = amount
                    onAmountSelected(amount)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedAmount == amount) YellowMellow else Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(
                    1.dp,
                    if (selectedAmount == amount) Color.Black else Color.Gray
                ),
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)

            ) {
                Text(
                    text = amount,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}


@Composable
private fun TabLayout(selectedTab: Int, tabs: List<Int>, onTabSelected: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedTab, containerColor = YellowMellow) {
        tabs.forEachIndexed { index, item ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = item),
                        contentDescription = "Tab Icon",
                        colorFilter = ColorFilter.tint(
                            if (selectedTab == index) Color.Gray else Color.Gray.copy(
                                alpha = 0.4f
                            )
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun SubTabLayout(
    selectedSubTab: Int,
    subTabs: List<String>,
    onSubTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedSubTab,
        containerColor = YellowMellow,
    ) {
        subTabs.forEachIndexed { index, item ->
            Tab(
                selected = selectedSubTab == index,
                onClick = { onSubTabSelected(index) },
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = item,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 48.sp,
                    color = if (selectedSubTab == index) Color.Black else Color.Gray,
                )
            }
        }
    }

}


@Composable
fun NavigationDrawerContent(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
            .fillMaxWidth(0.7f)
            .verticalScroll(rememberScrollState())
    ) {
        DrawerHeader()
        DrawerItems(
            listOf("Logout", "Share App" ,"Help"),
            onItemClick = { item ->
                when (item) {
                    "Logout" -> {
                        coroutineScope.launch {
                            DataStorageManager.logout(context)
                            navController.navigate("login_screen") {
                                popUpTo("home_screen") { inclusive = true }
                            }
                        }
                    }

                    "Help" -> {
                        Toast.makeText(context,"Please call us at 900900", Toast.LENGTH_LONG).show()
                    }

                    "Share App" -> {
                        shareApp(context)
                    }
                }
            }
        )
    }
}

private fun shareApp(context: Context) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Check out this amazing app!")
        putExtra(Intent.EXTRA_TEXT, "Download the app from: https://play.google.com/store/apps/details?id=com.example.khabar")
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}

@Composable
private fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(YellowMellow)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(R.drawable.ic_profile),
            contentDescription = "Profile Photo",
            modifier = Modifier.size(96.dp),
            colorFilter = ColorFilter.tint(Color.Gray)
        )
        Text(
            text = "Distributor 1",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DrawerItems(items: List<String>, onItemClick: (String) -> Unit) {
    Column(modifier = Modifier.padding(8.dp)) {
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .clickable { onItemClick(item) }
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    lineHeight = 48.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            HorizontalDivider(thickness = 2.dp, color = Color.Gray.copy(alpha = 0.2f))
        }
    }
}

private fun getTabTitle(index: Int) = when (index) {
    0 -> "Home"
    1 -> "Transactions"
    else -> "Reports"
}

@Composable
fun SuccessBottomSheetContent(
    title: String,
    amount: String,
    number: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Success Icon
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            tint = Color.Green,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Success Message
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Transaction Details
        TransactionDetailRow("CUSTOMER", number)
        Spacer(modifier = Modifier.height(24.dp))

        TransactionDetailRow("AMOUNT", "$$amount")

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { Toast.makeText(context,"This feature will be updated shortly !", Toast.LENGTH_SHORT).show() },
                colors = ButtonDefaults.buttonColors(containerColor = YellowMellow),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Print Receipt", color = Color.Black)
            }

            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Dismiss", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun TransactionDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, color = Color.Gray)
        Text(text = value, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

