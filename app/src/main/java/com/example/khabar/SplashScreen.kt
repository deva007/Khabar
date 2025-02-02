package com.example.khabar

import DataStorageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.khabar.ui.theme.YellowMellow
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))
    val progress by animateLottieCompositionAsState(composition)
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        DataStorageManager.getLoggedIn(context).collect { isLoggedIn ->
            if (isLoggedIn) {
                navController.navigate("home_screen") {
                    popUpTo("splash_screen") { inclusive = true } // Remove splash screen from back stack
                    launchSingleTop = true
                }
            } else {
                delay(2000)
                navController.navigate("country_screen") { // Navigate to login screen
                    popUpTo("splash_screen") { inclusive = true } // Remove splash screen from back stack
                    launchSingleTop = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowMellow),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.size(300.dp)
        )
    }
}

