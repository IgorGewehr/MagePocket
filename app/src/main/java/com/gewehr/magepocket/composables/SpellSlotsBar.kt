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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gewehr.magepocket.R
import com.gewehr.magepocket.data.SpellSlotViewModel

@Composable
fun SpellSlotsBar(spellSlotViewModel: SpellSlotViewModel) {
    val maxSlots = 4
    // Coleta os slots de magia do ViewModel
    val spellSlots by spellSlotViewModel.spellSlots.collectAsState()

    // Column para os níveis de magia de I a IX
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(2.dp),
        horizontalAlignment = Alignment.Start
    ) {
        spellSlots.take(9).forEachIndexed { index, spellSlot ->
            val level = index + 1

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Exibe o número romano do nível de magia
                Text(
                    text = getRomanNumeral(level),
                    color = Color.Black,
                    modifier = Modifier.width(26.dp)
                )

                // Botão para adicionar/remover slots
                IconButton(onClick = {
                    val newSlotCount = if (spellSlot.slots < maxSlots) {
                        spellSlot.slots + 1
                    } else {
                        0 // Reseta os slots ao atingir o máximo
                    }
                    // Atualiza a lista de cores dos slots
                    val newColors = spellSlot.colors.take(newSlotCount) + List(maxSlots - newSlotCount) { false }
                    spellSlotViewModel.saveSpellSlot(level, newSlotCount, newColors)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.grimore_icon),
                        contentDescription = "Adicionar Slot",
                        tint = Color.Black
                    )
                }

                // Exibe os quadrados de slots em duas colunas
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    for (row in 0 until 2) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            for (j in row until spellSlot.slots step 2) {
                                val color = spellSlot.colors[j]
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(if (color) Color.Red else Color.Blue)
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
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// Função para converter números inteiros em algarismos romanos
fun getRomanNumeral(number: Int): String {
    val romanNumerals = listOf(
        "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"
    )
    return if (number in 1..9) romanNumerals[number - 1] else number.toString()
}
