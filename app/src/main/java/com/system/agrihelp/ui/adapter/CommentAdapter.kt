package com.system.agrihelp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.system.agrihelp.data.models.Comment
import com.system.agrihelp.databinding.CommentRowLayoutBinding
import com.system.agrihelp.utility.TimeAgo
import java.util.*

class CommentAdapter(
    private val onComment: (Comment) -> Unit
) : ListAdapter<Comment, CommentAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CommentRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onComment)
    }


    class ViewHolder(val binding: CommentRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comment, onComment: (Comment) -> Unit) {
            Log.d("CheckingItemDetails", "bind: ${item.date}")
            Log.d("CheckingItemDetails", "bind: ${item.date?.toDate()}")

            binding.commentTextContent.text = item.comment
            binding.commentUserName.text = item.username

            convertTime(item.date!!)
        }

        private fun convertTime(date: Timestamp) {
            val dateString = TimeAgo.getTimeAgo((date.seconds * 1000))
            binding.commentPublishTime.text = dateString
        }

    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem == newItem
            }

        }
    }

}