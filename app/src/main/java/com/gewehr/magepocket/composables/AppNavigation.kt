package com.gewehr.magepocket.composables

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "firstScreen") {
        composable("firstScreen") { FirstScreen(navController) }
        composable("Inventory") { InventoryScreen(navController) }
        composable("Grimore") { GrimoreScreen(navController) }
    }
}
