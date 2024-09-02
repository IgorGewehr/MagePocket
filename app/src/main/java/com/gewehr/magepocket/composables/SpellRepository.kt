package com.gewehr.magepocket.composables

import android.content.Context
import com.gewehr.magepocket.data.Spell
import com.gewehr.magepocket.data.loadSpellsFromJson

object SpellRepository {
    fun loadSpells(context: Context): List<Spell> {
        return loadSpellsFromJson(context)
    }
}
