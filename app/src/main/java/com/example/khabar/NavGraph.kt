package com.example.khabar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
val TAG = "NavGraph"
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navigateToLogin = { navController.navigate("country_screen") })
        }

        composable("country_screen") {
            CountrySelectionScreen(onCountrySelected = { it ->
                Log.d(TAG, "Country == ${it}")

            }, onLanguageSelected = { it ->
                Log.d(TAG, "Language == ${it}")
            }) {
                navController.navigate("login_screen")
            }
        }
        composable("login_screen") {
            LoginScreen(navController)
        }
        composable("home_screen") {
           // HomeScreen()
        }
        composable("transactions_screen") {
           // TransactionsScreen()
        }
        composable("purchases_screen") {
           // PurchasesScreen()
        }
        // Add more screens as needed
    }
}