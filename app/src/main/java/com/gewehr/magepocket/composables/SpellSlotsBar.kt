import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gewehr.magepocket.R
import com.gewehr.magepocket.data.SpellSlotViewModel
import com.gewehr.magepocket.data.SpellSlot

// Função que cria uma lista de spell slots por nível de magia
@Composable
fun SpellSlotsBar(spellSlotViewModel: SpellSlotViewModel) {
    val maxSlots = 4

    // Coletar o estado atual dos slots do ViewModel
    val spellSlots by spellSlotViewModel.spellSlots.collectAsState(initial = List(9) { 0 })

    var singleColumn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        for (i in 0 until 9) {
            val level = i + 1
            val slots = spellSlots[i]

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Mostrar o número romano
                Text(
                    text = getRomanNumeral(level),
                    color = Color.Black,
                    modifier = Modifier.width(30.dp)  // Mantém o número em posição fixa
                )

                // Botão de adicionar slot
                IconButton(onClick = {
                    val newSlotCount = if (slots < maxSlots) {
                        slots + 1
                    } else {
                        0 // Reseta os slots se atingir o máximo
                    }
                    spellSlotViewModel.saveSpellSlot(level, newSlotCount)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.grimore_icon),
                        contentDescription = "Adicionar Slot",
                        tint = Color.Black
                    )
                }

                // Coluna para os slots
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (singleColumn) {
                        // Uma coluna
                        for (j in 0 until slots) {
                            val isUsed = remember { mutableStateOf(false) }
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isUsed.value) Color.Red else Color.Blue)
                                    .padding(4.dp)
                                    .clickable { isUsed.value = !isUsed.value }
                            )
                        }
                    } else {
                        // Duas colunas
                        for (row in 0 until 2) {
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                for (j in row until slots step 2) {
                                    val isUsed = remember { mutableStateOf(false) }
                                    Box(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (isUsed.value) Color.Red else Color.Blue)
                                            .padding(4.dp)
                                            .clickable { isUsed.value = !isUsed.value }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp)) // Espaço entre os níveis
        }
    }
}



// Função para converter números em números romanos
fun getRomanNumeral(number: Int): String {
    val romanNumerals = listOf(
        "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"
    )
    return if (number in 1..9) romanNumerals[number - 1] else number.toString()
}

// Componente de busca de magias (placeholder)
@Composable
fun MagicSearchBar() {
    // Aqui pode ser implementada a lógica para o buscador de magias
    Text(
        text = "Search Bar Placeholder",
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Gray)
            .padding(8.dp)
    )
}
