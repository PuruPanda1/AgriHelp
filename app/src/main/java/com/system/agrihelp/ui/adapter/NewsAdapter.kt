package com.system.agrihelp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.system.agrihelp.data.models.Article
import com.system.agrihelp.databinding.NewsRowLayoutBinding

class NewsAdapter(
    val onClick: (item: Article) -> Unit
) : ListAdapter<Article, NewsAdapter.ViewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClick)
    }

    class ViewHolder(private val binding: NewsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Article,
            onClick: (item: Article) -> Unit
        ) {
            binding.newsTitle.text = item.title
            binding.newsSource.text = item.source.name
            binding.root.setOnClickListener {
                onClick(item)
            }
//            binding.newsPublishedAt.text = item.publishedAt
            Glide.with(binding.root.context)
                .load(item.urlToImage)
                .into(binding.newsImage)
        }
    }

    class Comparator : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
}