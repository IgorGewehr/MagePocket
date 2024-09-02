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
    val spellSlots by spellSlotViewModel.spellSlots.collectAsState()

    var singleColumn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(2.dp),
        horizontalAlignment = Alignment.Start
    ) {
        for (i in spellSlots.indices) {
            val level = i + 1
            val spellSlot = spellSlots[i]

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = getRomanNumeral(level),
                    color = Color.Black,
                    modifier = Modifier.width(26.dp)
                )

                IconButton(onClick = {
                    val newSlotCount = if (spellSlot.slots < maxSlots) {
                        spellSlot.slots + 1
                    } else {
                        0 // Reseta os slots se atingir o máximo
                    }
                    val newColors = spellSlot.colors.take(newSlotCount) + List(maxSlots - newSlotCount) { false }
                    spellSlotViewModel.saveSpellSlot(level, newSlotCount, newColors)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.grimore_icon),
                        contentDescription = "Adicionar Slot",
                        tint = Color.Black
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (singleColumn) {
                        for (j in 0 until spellSlot.slots) {
                            val color = spellSlot.colors[j]
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (color) Color.Red else Color.Blue)
                                    .padding(4.dp)
                                    .clickable {
                                        val newColors = spellSlot.colors.toMutableList()
                                        newColors[j] = !color
                                        spellSlotViewModel.saveSpellSlot(level, spellSlot.slots, newColors)
                                    }
                            )
                        }
                    } else {
                        for (row in 0 until 2) {
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                for (j in row until spellSlot.slots step 2) {
                                    val color = spellSlot.colors[j]
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (color) Color.Red else Color.Blue)
                                            .padding(4.dp)
                                            .clickable {
                                                val newColors = spellSlot.colors.toMutableList()
                                                newColors[j] = !color
                                                spellSlotViewModel.saveSpellSlot(level, spellSlot.slots, newColors)
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
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

