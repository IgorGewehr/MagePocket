package com.gewehr.magepocket.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InventoryItemDao {
    @Query("SELECT * FROM inventory_items")
    suspend fun getAllItems(): List<InventoryItem>

    @Query("SELECT * FROM inventory_items WHERE category = :category")
    suspend fun getItemsByCategory(category: String): List<InventoryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: InventoryItem)

    @Delete
    suspend fun deleteItem(item: InventoryItem)
}

