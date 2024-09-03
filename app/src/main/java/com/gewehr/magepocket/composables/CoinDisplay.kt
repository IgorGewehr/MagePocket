import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CoinsDisplay(
    gold: Int,
    silver: Int,
    bronze: Int,
    onGoldChange: (Int) -> Unit,
    onSilverChange: (Int) -> Unit,
    onBronzeChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            CoinField(label = "Gold", value = gold, onValueChange = onGoldChange)
            Spacer(modifier = Modifier.width(8.dp))
            CoinField(label = "Silver", value = silver, onValueChange = onSilverChange)
            Spacer(modifier = Modifier.width(8.dp))
            CoinField(label = "Bronze", value = bronze, onValueChange = onBronzeChange)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinField(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5E3C16),
                textAlign = TextAlign.Center
            )
        )
        OutlinedTextField(
            value = value.toString(),
            onValueChange = { newValue ->
                newValue.toIntOrNull()?.let { onValueChange(it) }
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF5E3C16),
                textAlign = TextAlign.Center
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF5E3C16),
                unfocusedBorderColor = Color(0xFF5E3C16),
                cursorColor = Color(0xFF5E3C16)
            ),
            modifier = Modifier.width(100.dp)
        )
    }
}
