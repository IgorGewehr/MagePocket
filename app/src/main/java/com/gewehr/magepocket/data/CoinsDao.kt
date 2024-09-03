package com.gewehr.magepocket.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinsDao {
    @Query("SELECT * FROM coins WHERE id = 1")
    suspend fun getCoins(): Coins?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoins(coins: Coins)
}
