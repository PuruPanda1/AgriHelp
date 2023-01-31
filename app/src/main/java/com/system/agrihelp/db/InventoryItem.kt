package com.system.agrihelp.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "inventory_items")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val bio: String,
    val isInput: Boolean,
    val quantity: Float,
    val date:Long
):Parcelable
