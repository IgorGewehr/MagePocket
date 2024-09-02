package com.gewehr.magepocket.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

fun loadSpellsFromJson(context: Context): List<Spell>{
    val inputStream = context.assets.open("allSpells.json")
    val reader = InputStreamReader(inputStream)
    val type = object : TypeToken<List<Spell>>() {}.type
    return Gson().fromJson(reader, type)
}