package com.gewehr.magepocket.data
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

@Database(entities = [SpellSlot::class, InventoryItem::class, Coins::class], version = 7)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun spellSlotDao(): SpellSlotDao
    abstract fun inventoryItemDao(): InventoryItemDao
    abstract fun coinsDao(): CoinsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration() // Força a destruição e recriação do banco de dados
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
