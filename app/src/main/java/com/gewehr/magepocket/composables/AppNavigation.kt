package com.gewehr.magepocket.composables

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gewehr.magepocket.data.SpellSlotViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val spellSlotViewModel: SpellSlotViewModel = viewModel()


    NavHost(navController = navController, startDestination = "firstScreen") {
        composable("firstScreen") { FirstScreen(navController) }
        composable("Inventory") { InventoryScreen(navController) }
        composable("Grimore") { GrimoreScreen(navController, spellSlotViewModel) }
    }
}
