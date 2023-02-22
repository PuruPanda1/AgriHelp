package com.purabmodi.agrihelp.data.repository

import com.purabmodi.agrihelp.db.InventoryDao
import com.purabmodi.agrihelp.db.InventoryItem
import javax.inject.Inject

class InventoryRepository @Inject constructor(private val inventoryDao: InventoryDao) {
    val readAllItems = inventoryDao.getAllItems()

    suspend fun insertItem(item: InventoryItem){
        inventoryDao.insertItem(item)
    }

    suspend fun updateItem(item: InventoryItem){
        inventoryDao.updateItem(item)
    }

    suspend fun deleteItem(item: InventoryItem){
        inventoryDao.deleteItem(item)
    }
}