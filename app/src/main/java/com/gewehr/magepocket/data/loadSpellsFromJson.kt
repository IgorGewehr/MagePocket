package com.gewehr.magepocket.data

import android.content.Context
import android.util.Log
import com.gewehr.magepocket.data.Spell
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

fun loadSpellsFromJson(context: Context): List<Spell> {
    val inputStream = context.assets.open("allSpells.json")
    val reader = InputStreamReader(inputStream)
    val json = reader.readText()
    reader.close()

    Log.d("LoadSpellsFromJson", "JSON lido: $json") // Adiciona um log para verificar o JSON lido

    val type = object : TypeToken<List<Spell>>() {}.type

    // Tenta desserializar o JSON e captura possíveis exceções
    return try {
        Gson().fromJson(json, type)
    } catch (e: Exception) {
        Log.e("LoadSpellsFromJson", "Erro ao desserializar o JSON", e)
        emptyList() // Retorna uma lista vazia em caso de erro
    }
}