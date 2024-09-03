package com.gewehr.magepocket.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val inventoryItemDao = AppDatabase.getDatabase(application).inventoryItemDao()

    private val _inventoryItems = MutableStateFlow(emptyList<InventoryItem>())
    val inventoryItems: StateFlow<List<InventoryItem>> = _inventoryItems

    // Moedas dos jogadores
    private val _gold = MutableStateFlow(100)
    val gold: StateFlow<Int> = _gold

    private val _silver = MutableStateFlow(50)
    val silver: StateFlow<Int> = _silver

    private val _bronze = MutableStateFlow(25)
    val bronze: StateFlow<Int> = _bronze

    init {
        loadInventoryItems()
    }

    private fun loadInventoryItems() {
        viewModelScope.launch {
            _inventoryItems.value = inventoryItemDao.getAllItems()
            // Aqui você poderia carregar os valores das moedas do banco de dados, se necessário.
        }
    }

    fun addItem(name: String, category: String) {
        viewModelScope.launch {
            val item = InventoryItem(name = name, category = category)
            inventoryItemDao.insertItem(item)
            loadInventoryItems()
        }
    }

    fun removeItem(item: InventoryItem) {
        viewModelScope.launch {
            inventoryItemDao.deleteItem(item)
            loadInventoryItems() // Recarrega a lista de itens
        }
    }

    // Funções para atualizar e salvar as moedas no banco de dados
    fun updateGold(newGold: Int) {
        _gold.value = newGold
        // Aqui você pode salvar no banco de dados.
    }

    fun updateSilver(newSilver: Int) {
        _silver.value = newSilver
        // Aqui você pode salvar no banco de dados.
    }

    fun updateBronze(newBronze: Int) {
        _bronze.value = newBronze
        // Aqui você pode salvar no banco de dados.
    }
}
