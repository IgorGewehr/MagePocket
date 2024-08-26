package com.gewehr.magepocket.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gewehr.magepocket.R
import kotlinx.coroutines.delay

@Composable
fun FirstScreen(navController: NavHostController) {
    var selectedBox by remember { mutableStateOf<String?>(null) }

    val firstBoxWeight by animateFloatAsState(
        targetValue = if (selectedBox == "Inventory") 1f else 0.5f,
        animationSpec = tween(durationMillis = 500)
    )

    val secondBoxWeight by animateFloatAsState(
        targetValue = if (selectedBox == "Grimore") 1f else 0.5f,
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0E6D2))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(firstBoxWeight)
                .background(Color(0xFFEEE8AA))
                .padding(16.dp)
                .clickable {
                    selectedBox = "Inventory"
                }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.inventory_icon),
                    contentDescription = "Inventory Icon",
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Inventory",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Cursive,
                        color = Color(0xFF5E3C16)
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.items),
                        contentDescription = null
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(secondBoxWeight)
                .background(Color(0xFFD2B48C))
                .padding(16.dp)
                .clickable {
                    selectedBox = "Grimore"
                }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.grimore_icon),
                    contentDescription = "Grimoire Icon",
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Grimoire",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Cursive,
                        color = Color(0xFF5E3C16)
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.grimoire),
                        contentDescription = null
                    )
                }
            }
        }
    }

    // Navegação após 1 segundo da animação
    LaunchedEffect(selectedBox) {
        selectedBox?.let {
            delay(1000)  // Espera 1 segundo
            navController.navigate(it)  // Navega para a rota correta
        }
    }
}
