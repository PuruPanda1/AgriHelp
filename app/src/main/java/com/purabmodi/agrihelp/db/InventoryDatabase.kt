package com.purabmodi.agrihelp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [InventoryItem::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun inventoryDao(): InventoryDao

    companion object {
        @Volatile
        private var INSTANCE: InventoryDatabase? = null

        fun getInstance(context: Context): InventoryDatabase{
            val tempIns = INSTANCE
            if(tempIns != null)
            {
                return tempIns
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InventoryDatabase::class.java,
                    "inventory_item_database"
                ).build()
                return instance
            }
        }
    }
}