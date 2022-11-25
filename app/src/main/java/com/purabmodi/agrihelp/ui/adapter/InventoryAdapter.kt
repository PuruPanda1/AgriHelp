package com.purabmodi.agrihelp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.purabmodi.agrihelp.databinding.InventoryItemRowLayoutBinding
import com.purabmodi.agrihelp.db.InventoryItem

class InventoryAdapter(
    val updateItem: (item: InventoryItem) -> Unit,
    val deleteItem: (item: InventoryItem) -> Unit
) : androidx.recyclerview.widget.ListAdapter<InventoryItem, InventoryAdapter.ViewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = InventoryItemRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, updateItem, deleteItem)
    }

    class ViewHolder(private val binding: InventoryItemRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: InventoryItem,
            updateItem: (item: InventoryItem) -> Unit,
            deleteItem: (item: InventoryItem) -> Unit
        ) {
            binding.itemName.text = item.name
            binding.itemBio.text = item.bio
            binding.itemId.text = item.id.toString()
            binding.itemQuantity.text = item.quantity.toString()
        }
    }

    class Comparator : DiffUtil.ItemCallback<InventoryItem>() {
        override fun areItemsTheSame(oldItem: InventoryItem, newItem: InventoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: InventoryItem, newItem: InventoryItem): Boolean {
            return oldItem == newItem
        }

    }
}