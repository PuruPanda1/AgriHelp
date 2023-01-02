package com.purabmodi.agrihelp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.purabmodi.agrihelp.data.models.Record
import com.purabmodi.agrihelp.databinding.MarketplaceRowItemLayoutBinding

class LatestMarketDataPagingAdapter(
    private val onClick: (item: Record) -> Unit
) : PagingDataAdapter<Record, LatestMarketDataPagingAdapter.JobViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, onClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding =
            MarketplaceRowItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return JobViewHolder(binding)
    }

    class JobViewHolder(val binding: MarketplaceRowItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Record, onClick: (item: Record) -> Unit) {
            binding.itemName.text = item.commodity
            binding.itemPrice.text = "₹ ${item.modal_price}"
            val text = "${item.district}, ${item.state}"
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Record>() {
            override fun areItemsTheSame(
                oldItem: Record,
                newItem: Record
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Record,
                newItem: Record
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}