import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gewehr.magepocket.R

// Função que cria uma lista de spell slots por nível de magia
@Composable
fun SpellSlotsBar() {
    val maxSlots = 4
    val spellSlots = remember {
        mutableStateListOf(
            0, // Level 1
            0, // Level 2
            0, // Level 3
            0, // Level 4
            0, // Level 5
            0, // Level 6
            0, // Level 7
            0, // Level 8
            0  // Level 9
        )
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                    color = Color.Black
                )

                // Botão de adicionar slot
                IconButton(onClick = {
                    if (slots < maxSlots) {
                        spellSlots[i] = slots + 1
                    } else {
                        spellSlots[i] = 0 // Reseta os slots se atingir o máximo
                    }
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
                    for (row in 0 until 2) { // Duas linhas
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            for (j in row until slots step 2) {
                                val isUsed = remember { mutableStateOf(false) }

                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(if (isUsed.value) Color.Red else Color.Blue)
                                        .padding(4.dp)
                                        .clickable {
                                            isUsed.value = !isUsed.value
                                        }
                                )
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
