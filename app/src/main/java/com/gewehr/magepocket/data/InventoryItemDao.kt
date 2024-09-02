package com.gewehr.magepocket.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InventoryItemDao {

    @Query("SELECT * FROM inventory_items")
    suspend fun getAllItems(): List<InventoryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: InventoryItem)
}
