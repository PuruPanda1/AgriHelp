package com.purabmodi.agrihelp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_items")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val bio: String,
    val quantity: Float,
)
