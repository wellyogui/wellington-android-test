package com.wellignton.androidtest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wellignton.androidtest.data.model.CommentItemView
import com.wellignton.androidtest.databinding.ItemCommentBinding
import javax.inject.Singleton

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
@Singleton
class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<CommentItemView>() {
        override fun areItemsTheSame(oldItem: CommentItemView, newItem: CommentItemView): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CommentItemView,
            newItem: CommentItemView
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = differ.currentList[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(commentItemView: CommentItemView) {
            with(binding) {
                nameView.text = commentItemView.name
                emailView.text = commentItemView.email
                commentView.text = commentItemView.body
            }
        }
    }
}