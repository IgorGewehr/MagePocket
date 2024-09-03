package com.gewehr.magepocket.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class Coins(
    @PrimaryKey val id: Int = 1, // Usamos um ID fixo, já que teremos uma única linha para as moedas
    val gold: Int,
    val silver: Int,
    val bronze: Int
)