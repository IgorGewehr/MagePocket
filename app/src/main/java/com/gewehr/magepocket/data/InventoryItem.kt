package com.gewehr.magepocket.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_items")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String, // Pode ser "Armors", "Weapons", "Coins", etc.
    val value: Int = 0 // Para armazenar valores das moedas
)
