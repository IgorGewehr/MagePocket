package com.gewehr.magepocket.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.gewehr.magepocket.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrimoreScreen(navController: NavHostController){
    Scaffold(
        topBar = {
            TopAppBar(title = {"Grimore"}, navigationIcon = { IconButton(onClick = {navController.navigate("FirstScreen")}) {
                Icon(painterResource(id = com.gewehr.magepocket.R.drawable.back_arrowpng),
                    contentDescription = null)
            }})
        }
    ) {it ->
        Column(modifier = Modifier.padding(it)){}
    }
}


