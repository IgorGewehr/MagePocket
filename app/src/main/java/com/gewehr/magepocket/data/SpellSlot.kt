package com.gewehr.magepocket.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spell_slots")
data class SpellSlot(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val level: Int,
    val slots: Int,
    val colors: List<Boolean>
)
