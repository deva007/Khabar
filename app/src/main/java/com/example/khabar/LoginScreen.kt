import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.khabar.R
import com.example.khabar.ui.theme.YellowMellow
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var msisdn by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var tabOneSelected by remember { mutableStateOf(true) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var pinError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_back_arrow),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color.White),
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Welcome back,",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Sign in to your EVD reseller app",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(48.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(
                    modifier = Modifier.weight(.8f).padding(4.dp),
                    onClick = { tabOneSelected = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tabOneSelected) YellowMellow else Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "Reseller ID", fontWeight = FontWeight.Bold)
                }
                Button(
                    modifier = Modifier.weight(0.8f).padding(4.dp),
                    onClick = { tabOneSelected = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!tabOneSelected) YellowMellow else Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "MSISDN", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            OutlinedTextField(
                value = msisdn,
                onValueChange = {
                    msisdn = it
                    phoneError = null // Reset error on input change
                },
                label = { Text(if (tabOneSelected) "Reseller ID" else "MSISDN") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(32.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_phone),
                        contentDescription = "Phone Icon"
                    )
                },
                isError = phoneError != null,
                textStyle = LocalTextStyle.current.copy(color = Color.Gray)


            )
            if (phoneError != null) {
                Text(text = phoneError!!, color = Color.Red, modifier = Modifier.padding(start = 16.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pin,
                onValueChange = {
                    pin = it
                    pinError = null
                },
                label = { Text("PIN") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_lock),
                        contentDescription = "Lock Icon"
                    )
                },
                isError = pinError != null,
                textStyle = LocalTextStyle.current.copy(color = Color.Gray)
            )
            if (pinError != null) {
                Text(text = pinError!!, color = Color.Red, modifier = Modifier.padding(start = 16.dp))
            }
        }

        Column {
            Button(
                onClick = {
                    if (msisdn.length !in 10..14) {
                        phoneError = "Phone number must be between 10 and 14 digits"
                    }
                    if (pin.length < 4) {
                        pinError = "PIN must be at least 4 digits"
                    }

                    if (phoneError == null && pinError == null) {
                        coroutineScope.launch {
                            DataStorageManager.saveUserData(context, true, msisdn, pin)
                        }
                        navController.navigate("onboarding")                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowMellow,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Login", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
