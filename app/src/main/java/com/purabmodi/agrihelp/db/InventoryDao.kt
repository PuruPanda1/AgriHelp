package com.purabmodi.agrihelp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface InventoryDao {
    @Insert
    suspend fun insertItem(inventoryItem: InventoryItem)

    @Update
    suspend fun updateItem(inventoryItem: InventoryItem)

    @Delete
    suspend fun deleteItem(inventoryItem: InventoryItem)

    @Query("SELECT * FROM inventory_items")
    fun getAllItems(): LiveData<List<InventoryItem>>
}