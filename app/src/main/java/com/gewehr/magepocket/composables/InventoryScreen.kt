package com.gewehr.magepocket.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gewehr.magepocket.data.InventoryViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(navController: NavHostController) {
    val inventoryViewModel: InventoryViewModel = viewModel()
    val inventoryItems by inventoryViewModel.inventoryItems.collectAsState()
    var newItem by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Inventory",
                        fontSize = 36.sp,
                        color = Color(0xFF5E3C16),
                        modifier = Modifier.padding(16.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("FirstScreen") }) {
                        Icon(
                            painterResource(id = com.gewehr.magepocket.R.drawable.back_arrowpng),
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEEE8AA),
                    titleContentColor = Color(0xFF5E3C16),
                    navigationIconContentColor = Color(0xFF5E3C16)
                )
            )
        },
        containerColor = Color(0xFFEEE8AA)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Campo de texto para adicionar um novo item
            TextField(
                value = newItem,
                onValueChange = { newItem = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Text("Enter item name")
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF5E3C16)
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    cursorColor = Color(0xFF5E3C16),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Button(
                onClick = {
                    if (newItem.isNotBlank()) {
                        inventoryViewModel.addItem(newItem)
                        newItem = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5E3C16),
                    contentColor = Color.White
                )
            ) {
                Text("Add Item")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de itens
            LazyColumn {
                items(inventoryItems) { item ->
                    Text(
                        text = item.name,
                        fontSize = 18.sp,
                        color = Color(0xFF5E3C16),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color(0xFFEDE0C8), shape = MaterialTheme.shapes.medium)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}
