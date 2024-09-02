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

    init {
        loadInventoryItems()
    }

    private fun loadInventoryItems() {
        viewModelScope.launch {
            _inventoryItems.value = inventoryItemDao.getAllItems()
        }
    }

    fun addItem(name: String) {
        viewModelScope.launch {
            val item = InventoryItem(name = name)
            inventoryItemDao.insertItem(item)
            loadInventoryItems() // Recarrega a lista de itens
        }
    }
}
