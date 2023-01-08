package com.purabmodi.agrihelp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.purabmodi.agrihelp.data.models.Comment
import com.purabmodi.agrihelp.databinding.CommentRowLayoutBinding
import java.text.SimpleDateFormat
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
//            binding.commentPublishTime.text = item.date.toString()

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