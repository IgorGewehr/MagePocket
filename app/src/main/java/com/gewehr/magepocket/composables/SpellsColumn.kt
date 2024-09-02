package com.gewehr.magepocket.composables

import android.util.Log
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
    var selectedClass by remember { mutableStateOf("Class") }
    var selectedLevel by remember { mutableStateOf("Level") }
    var selectedSchool by remember { mutableStateOf("School") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color(0xFFEDE0C8))
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { query -> searchQuery = query },
            placeholder = {
                Text(
                    text = "Search spell...",
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

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de Feitiços
        SpellList(spells = filteredSpells)
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
    val classOptions = listOf("","Barbarian", "Bard", "Warlock", "Cleric", "Druid", "Sorcerer", "Fighter", "Rogue", "Wizard", "Monk", "Paladin", "Ranger")
    val levelOptions = listOf("","Cantrip", "1st Level", "2nd Level", "3rd Level", "4th Level", "5th Level", "6th Level", "7th Level", "8th Level", "9th Level")
    val schoolOptions = listOf("","Abjuration", "Divination", "Conjuration", "Enchantment", "Evocation", "Illusion", "Necromancy", "Transmutation")

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
            Text("Search", fontSize = 18.sp)
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
    Log.d("SpellList", "Displaying ${spells.size} spells")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize() // Certifica-se de que a LazyColumn usa todo o espaço disponível
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
            text = "Level: ${if (spell.level == 0) "Cantrip" else "${spell.level} Level"}",
            fontSize = 16.sp,
            color = Color(0xFF5E3C16)
        )
        Text(
            text = "School: ${spell.school}",
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
    Log.d("applyFilters", "Applying filters: nameQuery=$nameQuery, classFilter=$classFilter, levelFilter=$levelFilter, schoolFilter=$schoolFilter")

    return spells.filter { spell ->
        // Verificação do filtro de nome
        val matchesName = if (nameQuery.isNotBlank()) {
            spell.name.contains(nameQuery, true)
        } else {
            true
        }

        // Verificação do filtro de classe
        val matchesClass = if (classFilter != "Class" && classFilter.isNotBlank()) {
            val charFilter = classFilter.firstOrNull()
            charFilter?.let { filter ->
                spell.classes.any { it == filter }
            } ?: false //
        } else {
            true
        }

        // Verificação do filtro de nível
        val matchesLevel = if (levelFilter != "Level" && levelFilter.isNotBlank()) {
            val level = when (levelFilter) {
                "Cantrip" -> 0
                "1st Level" -> 1
                "2nd Level" -> 2
                "3rd Level" -> 3
                "4th Level" -> 4
                "5th Level" -> 5
                "6th Level" -> 6
                "7th Level" -> 7
                "8th Level" -> 8
                "9th Level" -> 9
                else -> -1  // Valor de fallback, se necessário
            }
            spell.level == level
        } else {
            true
        }

        // Verificação do filtro de escola
        val matchesSchool = if (schoolFilter != "School" && schoolFilter.isNotBlank()) {
            spell.school.equals(schoolFilter, ignoreCase = true)
        } else {
            true
        }

        // Retorna verdadeiro se todos os critérios corresponderem
        matchesName && matchesClass && matchesLevel && matchesSchool
    }.also { result ->
        Log.d("applyFilters", "Filtered result count: ${result.size}")
    }.take(13) // Limita a 20 resultados para desempenho
}

