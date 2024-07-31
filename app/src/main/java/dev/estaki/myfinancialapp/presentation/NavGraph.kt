package dev.estaki.myfinancialapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.estaki.myfinancialapp.presentation.AddDetailScreen
import dev.estaki.myfinancialapp.presentation.MainScreen

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