package dev.estaki.myFinancialApp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Nav(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "MainScreen") {
        composable(route = "MainScreen",
        ) {
            MainScreen(navController)
        }
        composable(route = "AddDetailScreen") {
            AddDetailScreen()
        }

    }
}