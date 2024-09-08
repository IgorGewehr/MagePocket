package com.gewehr.magepocket.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpellSlotViewModel(application: Application) : AndroidViewModel(application) {
    private val spellSlotDao = AppDatabase.getDatabase(application).spellSlotDao()

    private val _spellSlots = MutableStateFlow<List<SpellSlot>>(emptyList())
    val spellSlots: StateFlow<List<SpellSlot>> = _spellSlots

    init {
        loadSpellSlots()
    }

    private fun loadSpellSlots() {
        viewModelScope.launch {
            val slots = spellSlotDao.getAllSpellSlots()
            if (slots.isNotEmpty()) {
                _spellSlots.value = slots
            }
        }
    }

    fun saveSpellSlot(level: Int, slots: Int, colors: List<Boolean>) {
        viewModelScope.launch {
            val existingSlot = spellSlotDao.getSpellSlotByLevel(level)
            if (existingSlot != null) {
                // Atualiza o slot existente
                val updatedSlot = existingSlot.copy(slots = slots, colors = colors)
                spellSlotDao.updateSpellSlot(updatedSlot)
            } else {
                // Insere um novo slot
                val newSpellSlot = SpellSlot(level = level, slots = slots, colors = colors)
                spellSlotDao.insertSpellSlot(newSpellSlot)
            }
            loadSpellSlots() // Atualiza a lista de slots no UI
        }
    }
}
