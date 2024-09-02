package com.gewehr.magepocket.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gewehr.magepocket.data.Spell

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpellsColumn() {
    val context = LocalContext.current
    val allSpells = remember { SpellRepository.loadSpells(context) }
    var filteredSpells by remember { mutableStateOf(allSpells.take(5)) }

    var searchQuery by remember { mutableStateOf("") }
    var selectedClass by remember { mutableStateOf("Classe") }
    var selectedLevel by remember { mutableStateOf("Nível") }
    var selectedSchool by remember { mutableStateOf("Escola") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color(0xFFEDE0C8))
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    text = "Buscar magia...",
                    color = Color(0xFF5E3C16),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Cursive
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = Color(0xFFFAF0E6), shape = RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFAF0E6),
                cursorColor = Color(0xFF5E3C16),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Seção de Filtros Aprimorada
        ImprovedFilterSection(
            selectedClass = selectedClass,
            onClassSelected = { selectedClass = it },
            selectedLevel = selectedLevel,
            onLevelSelected = { selectedLevel = it },
            selectedSchool = selectedSchool,
            onSchoolSelected = { selectedSchool = it },
            onSearchClicked = {
                filteredSpells = applyFilters(
                    spells = allSpells,
                    nameQuery = searchQuery,
                    classFilter = selectedClass,
                    levelFilter = selectedLevel,
                    schoolFilter = selectedSchool
                )
            }
        )
    }
}

@Composable
fun ImprovedFilterSection(
    selectedClass: String,
    onClassSelected: (String) -> Unit,
    selectedLevel: String,
    onLevelSelected: (String) -> Unit,
    selectedSchool: String,
    onSchoolSelected: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    val classOptions = listOf("Bárbaro", "Bardo", "Bruxo", "Clérigo", "Druida", "Feiticeiro", "Guerreiro", "Ladino", "Mago", "Monge", "Paladino", "Patrulheiro")
    val levelOptions = listOf("Cantrip", "1º Nível", "2º Nível", "3º Nível", "4º Nível", "5º Nível", "6º Nível", "7º Nível", "8º Nível", "9º Nível")
    val schoolOptions = listOf("Abjuração", "Adivinhação", "Conjuração", "Encantamento", "Evocação", "Ilusão", "Necromancia", "Transmutação")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFFAF0E6),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        DropdownMenuWithLabel(
            label = selectedClass,
            options = classOptions,
            onOptionSelected = onClassSelected
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuWithLabel(
            label = selectedLevel,
            options = levelOptions,
            onOptionSelected = onLevelSelected
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuWithLabel(
            label = selectedSchool,
            options = schoolOptions,
            onOptionSelected = onSchoolSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de Pesquisa
        Button(
            onClick = onSearchClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5E3C16),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Pesquisar", fontSize = 18.sp)
        }
    }
}


@Composable
fun DropdownMenuWithLabel(
    label: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFD4C6A7), shape = RoundedCornerShape(8.dp))
            .clickable { expanded = true }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, color = Color(0xFF5E3C16))
            Icon(Icons.Filled.ArrowDropDown, contentDescription = null, tint = Color(0xFF5E3C16))
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFFAF0E6))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SpellList(spells: List<Spell>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFAF0E6), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        items(spells) { spell ->
            SpellItem(spell = spell)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SpellItem(spell: Spell) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFEDE0C8), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = spell.name,
            fontSize = 20.sp,
            fontFamily = FontFamily.Cursive,
            color = Color(0xFF5E3C16),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Nível: ${if (spell.level == 0) "Cantrip" else spell.level}",
            fontSize = 16.sp,
            color = Color(0xFF5E3C16)
        )
        Text(
            text = "Escola: ${spell.school}",
            fontSize = 16.sp,
            color = Color(0xFF5E3C16)
        )
        Text(
            text = "Classes: ${spell.classes}",
            fontSize = 16.sp,
            color = Color(0xFF5E3C16)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = spell.description,
            fontSize = 14.sp,
            color = Color(0xFF5E3C16)
        )
    }
}

fun applyFilters(
    spells: List<Spell>,
    nameQuery: String,
    classFilter: String,
    levelFilter: String,
    schoolFilter: String
): List<Spell> {
    return spells.filter { spell ->
        val matchesName = if (nameQuery.isNotBlank()) {
            spell.name.contains(nameQuery, ignoreCase = true)
        } else {
            true
        }

        val matchesClass = if (classFilter != "Classe") {
            spell.classes.contains(nameQuery, ignoreCase = true)
        } else {
            true
        }

        val matchesLevel = if (levelFilter != "Nível") {
            val level = if (levelFilter == "Cantrip") 0 else levelFilter.substringBefore("º").toInt()
            spell.level == level
        } else {
            true
        }

        val matchesSchool = if (schoolFilter != "Escola") {
            spell.school.equals(schoolFilter, ignoreCase = true)
        } else {
            true
        }

        matchesName && matchesClass && matchesLevel && matchesSchool
    }.take(20) // Limita a 20 resultados para desempenho
}