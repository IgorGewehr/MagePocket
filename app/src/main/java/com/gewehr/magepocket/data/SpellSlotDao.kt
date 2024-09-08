package com.gewehr.magepocket.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SpellSlotDao {
    @Query("SELECT * FROM spell_slots WHERE level = :level")
    suspend fun getSpellSlotByLevel(level: Int): SpellSlot?

    @Query("SELECT * FROM spell_slots")
    suspend fun getAllSpellSlots(): List<SpellSlot>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpellSlot(spellSlot: SpellSlot)

    @Update
    suspend fun updateSpellSlot(spellSlot: SpellSlot)
}
