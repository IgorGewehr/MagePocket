package com.gewehr.magepocket.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Import necessário
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SpellSlotViewModel(application: Application) : AndroidViewModel(application) {

    private val spellSlotDao = AppDatabase.getDatabase(application).spellSlotDao()

    // StateFlow para armazenar os slots
    private val _spellSlots = MutableStateFlow(List(9) { 0 })
    val spellSlots: StateFlow<List<Int>> = _spellSlots

    init {
        // Carregar os slots do banco de dados ao inicializar o ViewModel
        loadSpellSlots()
    }

    private fun loadSpellSlots() {
        viewModelScope.launch {
            // Obter todos os SpellSlots do banco de dados
            val slots = spellSlotDao.getAllSpellSlots()

            // Atualizar o StateFlow com os dados obtidos
            _spellSlots.update { currentSlots ->
                currentSlots.mapIndexed { index, _ ->
                    slots.find { it.level == index + 1 }?.slots ?: 0
                }
            }
        }
    }

    fun saveSpellSlot(level: Int, slots: Int) {
        viewModelScope.launch {
            val spellSlot = SpellSlot(level = level, slots = slots)
            spellSlotDao.insertSpellSlot(spellSlot)
            // Atualizar o StateFlow após salvar no banco de dados
            _spellSlots.update { currentSlots ->
                currentSlots.toMutableList().apply {
                    this[level - 1] = slots
                }
            }
        }
    }
}