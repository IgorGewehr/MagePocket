package com.gewehr.magepocket.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val inventoryItemDao = AppDatabase.getDatabase(application).inventoryItemDao()
    private val coinsDao = AppDatabase.getDatabase(application).coinsDao()

    private val _inventoryItems = MutableStateFlow(emptyList<InventoryItem>())
    val inventoryItems: StateFlow<List<InventoryItem>> = _inventoryItems

    private val _gold = MutableStateFlow(0)
    val gold: StateFlow<Int> = _gold

    private val _silver = MutableStateFlow(0)
    val silver: StateFlow<Int> = _silver

    private val _bronze = MutableStateFlow(0)
    val bronze: StateFlow<Int> = _bronze

    init {
        loadInventoryItems()
        loadCoins()
    }

    private fun loadInventoryItems() {
        viewModelScope.launch {
            _inventoryItems.value = inventoryItemDao.getAllItems()
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            val coins = coinsDao.getCoins()
            if (coins != null) {
                _gold.value = coins.gold
                _silver.value = coins.silver
                _bronze.value = coins.bronze
            }
        }
    }

    private fun saveCoins() {
        viewModelScope.launch {
            coinsDao.insertCoins(Coins(gold = _gold.value, silver = _silver.value, bronze = _bronze.value))
        }
    }

    fun updateGold(newGold: Int) {
        _gold.value = newGold
        saveCoins()
    }

    fun updateSilver(newSilver: Int) {
        _silver.value = newSilver
        saveCoins()
    }

    fun updateBronze(newBronze: Int) {
        _bronze.value = newBronze
        saveCoins()
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
            loadInventoryItems()
        }
    }
}
