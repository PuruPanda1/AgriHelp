package com.system.agrihelp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.system.agrihelp.data.models.Posts
import com.system.agrihelp.databinding.ForumRowLayoutBinding
import com.system.agrihelp.utility.TimeAgo
import java.text.SimpleDateFormat
import java.util.*

class ForumAdapter(
    private val onComment: (Posts) -> Unit,
) : ListAdapter<Posts, ForumAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ForumRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onComment)
    }


    class ViewHolder(val binding: ForumRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Posts, onComment: (Posts) -> Unit) {
            Log.d("CheckingItemDetails", "bind: ${item.date}")
            Log.d("CheckingItemDetails", "bind: ${item.date?.toDate()}")

            binding.blogTitleRV.text = item.title
            binding.blogDescriptionRV.text = item.description
            binding.blogHashTags.text = item.hashtags
            binding.blogUserNameRV.text = item.username

            convertTime(item.date!!)

            binding.blogCommentCountRV.setOnClickListener {
                onComment(item)
            }

        }

        private fun convertTime(date: Timestamp) {
            val dateString = TimeAgo.getTimeAgo((date.seconds * 1000))
            binding.dateTimeBlogRV.text = dateString
        }

        fun dateFormat(date: String): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val dateTime = simpleDateFormat.parse(date)
            val now = Calendar.getInstance().timeInMillis
            val result = android.text.format.DateUtils.getRelativeTimeSpanString(
                dateTime.time,
                now,
                android.text.format.DateUtils.MINUTE_IN_MILLIS
            )
            return result.toString()
        }
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