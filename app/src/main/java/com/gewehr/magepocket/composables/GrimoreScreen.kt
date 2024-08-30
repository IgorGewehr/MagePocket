package com.gewehr.magepocket.composables

import SpellSlotsBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.gewehr.magepocket.data.SpellSlot
import com.gewehr.magepocket.data.SpellSlotViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrimoreScreen(navController: NavHostController, spellSlotViewModel: SpellSlotViewModel){

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 50.dp),
                        contentAlignment = Alignment.Center){
                        Text(
                            text = "Grimore",
                            style = TextStyle(
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Cursive,
                                color = Color(0xFF5E3C16)
                            )
                        )
                    }
                },
                navigationIcon = { IconButton(onClick = {navController.navigate("FirstScreen")}) {
                    Icon(
                        painterResource(id = com.gewehr.magepocket.R.drawable.back_arrowpng),
                        contentDescription = null)
                }},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD2B48C),
                    titleContentColor = Color(0xFF5E3C16),
                    navigationIconContentColor = Color(0xFF5E3C16)
                )
            )
        },
        containerColor = Color(0xFFD2B48C)
    ) {it ->
        Row(modifier = Modifier.padding(it)){ SpellSlotsBar(spellSlotViewModel)}
    }
}