package com.system.agrihelp.di

import android.app.Application
import com.system.agrihelp.db.InventoryDao
import com.system.agrihelp.db.InventoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun getInventoryDatabase(context: Application):InventoryDatabase{
        return InventoryDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun getDao(inventoryDatabase: InventoryDatabase):InventoryDao{
        return inventoryDatabase.inventoryDao()
    }
}