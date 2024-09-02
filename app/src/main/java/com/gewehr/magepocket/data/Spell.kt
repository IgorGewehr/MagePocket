package com.gewehr.magepocket.data

data class Spell(
    val name: String,
    val classes: String,
    val level: Int,
    val school: String,
    val ritual: Boolean,
    val castingTime: String,
    val range: String,
    val components: String,
    val material: String?,
    val duration: String,
    val description: String,
    val source: String,
    val page: Int
)
