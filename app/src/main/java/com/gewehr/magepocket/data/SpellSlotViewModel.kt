package com.gewehr.magepocket.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SpellSlotViewModel(application: Application) : AndroidViewModel(application) {

    private val spellSlotDao = AppDatabase.getDatabase(application).spellSlotDao()

    // Não inicializar com valores padrão, deixar vazio inicialmente
    private val _spellSlots = MutableStateFlow<List<SpellSlot>>(emptyList())
    val spellSlots: StateFlow<List<SpellSlot>> = _spellSlots

    init {
        loadSpellSlots()
    }

    private fun loadSpellSlots() {
        viewModelScope.launch {
            val slots = spellSlotDao.getAllSpellSlots()
            // Apenas atualize a lista de slots se houver algo retornado do banco de dados
            if (slots.isNotEmpty()) {
                _spellSlots.value = slots
            } else {
                // Se não houver slots no banco de dados, inicialize com os valores padrão
                _spellSlots.value = List(9) { SpellSlot(level = it + 1, slots = 0, colors = List(4) { false }) }
            }
        }
    }

    fun saveSpellSlot(level: Int, slots: Int, colors: List<Boolean>) {
        viewModelScope.launch {
            val spellSlot = SpellSlot(level = level, slots = slots, colors = colors)
            spellSlotDao.insertSpellSlot(spellSlot)
            _spellSlots.update { currentSlots ->
                currentSlots.toMutableList().apply {
                    this[level - 1] = spellSlot
                }
            }
        }
    }
}
