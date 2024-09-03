import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.gewehr.magepocket.data.InventoryItem
import com.gewehr.magepocket.data.InventoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(navController: NavHostController) {
    val inventoryViewModel: InventoryViewModel = viewModel()
    val inventoryItems by inventoryViewModel.inventoryItems.collectAsState()
    val gold by inventoryViewModel.gold.collectAsState()
    val silver by inventoryViewModel.silver.collectAsState()
    val bronze by inventoryViewModel.bronze.collectAsState()
    val categories = listOf("Armors", "Weapons", "Food", "Alchemy")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Inventory",
                            style = TextStyle(
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Cursive,
                                color = Color(0xFF5E3C16)
                            )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("FirstScreen") }) {
                        Icon(
                            painter = painterResource(id = com.gewehr.magepocket.R.drawable.back_arrowpng),
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                CoinsDisplay(
                    gold = gold,
                    silver = silver,
                    bronze = bronze,
                    onGoldChange = { inventoryViewModel.updateGold(it) },
                    onSilverChange = { inventoryViewModel.updateSilver(it) },
                    onBronzeChange = { inventoryViewModel.updateBronze(it) }
                )
            }

            categories.forEach { category ->
                item {
                    ExpandableCategory(
                        category = category,
                        items = inventoryItems.filter { it.category == category },
                        onAddItem = { newItem ->
                            inventoryViewModel.addItem(newItem, category)
                        },
                        onRemoveItem = { item ->
                            inventoryViewModel.removeItem(item)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCategory(
    category: String,
    items: List<InventoryItem>,
    onAddItem: (String) -> Unit,
    onRemoveItem: (InventoryItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var newItem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEDE0C8), shape = MaterialTheme.shapes.medium)
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .padding(16.dp)
    ) {
        Text(
            text = category,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5E3C16),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp)
        )

        if (expanded) {
            items.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        fontSize = 18.sp,
                        color = Color(0xFF5E3C16)
                    )
                    IconButton(onClick = { onRemoveItem(item) }) {
                        Icon(
                            painter = painterResource(id = com.gewehr.magepocket.R.drawable.ic_delete),
                            contentDescription = "Delete item",
                            tint = Color.Red
                        )
                    }
                }
            }

            // Campo de texto para adicionar um novo item
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newItem,
                    onValueChange = { newItem = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    placeholder = { Text("Add new item") },
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
                            onAddItem(newItem)
                            newItem = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5E3C16),
                        contentColor = Color.White
                    )
                ) {
                    Text("Add")
                }
            }
        }
    }
}
