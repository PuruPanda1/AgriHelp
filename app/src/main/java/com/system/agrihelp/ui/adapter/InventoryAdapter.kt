package com.system.agrihelp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.system.agrihelp.R
import com.system.agrihelp.databinding.InventoryItemRowLayoutBinding
import com.system.agrihelp.db.InventoryItem
import java.text.SimpleDateFormat

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
            binding.itemNos.text = item.quantity.toString()

            val simpleDateFormat = SimpleDateFormat("dd MMM")
            val dateString = simpleDateFormat.format(item.date)
            binding.itemDate.text = dateString

            if(item.isInput){
                Glide.with(binding.root).load(R.drawable.ic_add_icon).into(binding.itemIcon)
                binding.itemCategory.text = "Planted"
            }else{
                Glide.with(binding.root).load(R.drawable.ic_remove_icon).into(binding.itemIcon)
                binding.itemCategory.text = "Removed"
            }


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