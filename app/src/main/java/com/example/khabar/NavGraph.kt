package com.example.khabar

import DataStorageManager
import LoginScreen
import SalesReportScreen
import android.content.Context
import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

val TAG = "NavGraph"

@Composable
fun NavGraph(navController: NavHostController, drawerState: DrawerState, context: Context) {
    val isLoggedIn by DataStorageManager.getLoggedIn(context).collectAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "home_screen" else "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navController)
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
            HomeScreen(navController, drawerState)
        }

        composable("onboarding") {
            OnboardingScreen(navController)
        }

        // sales screens

        composable("sales_report") {
            SalesReportScreen(navController)
        }
        composable("sales_details") {
            SalesDetailScreen(navController)
        }


        composable("transaction_history") {
            TransactionHistoryScreen(navController)
        }

        composable("transaction_detail") {
            TransactionHistoryDetailScreen(navController)
        }
        

        composable("deposit_history") {
            DepositHistoryScreen(navController)
        }

        composable("deposit_detail") {
            DepositDetailScreen(navController)
        }
    }
}