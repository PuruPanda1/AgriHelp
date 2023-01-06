package com.purabmodi.agrihelp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.purabmodi.agrihelp.data.models.Posts
import com.purabmodi.agrihelp.databinding.ForumRowLayoutBinding

class ForumAdapter : ListAdapter<Posts, ForumAdapter.ViewHolder>(COMPARATOR) {

    class ViewHolder(val binding: ForumRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Posts) {
            Log.d("CHECKINGSKILLS", "bind: $item.name")
            binding.blogTitleRV.text = item.title
            binding.blogDescriptionRV.text = item.description
            binding.blogHashTags.text = item.hashtags
//            binding.dateTimeBlogRV.text = item.date
            binding.blogLikeCountRV.text = item.likes.toString()
            binding.blogCommentCountRV.text = item.comments.toString()
            binding.blogUserNameRV.text = item.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ForumRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Posts>() {
            override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean {
                return oldItem == newItem
            }

        }
    }

}