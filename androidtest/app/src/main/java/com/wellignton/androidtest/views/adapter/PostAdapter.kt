package com.wellignton.androidtest.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wellignton.androidtest.databinding.ItemPostBinding
import com.wellignton.androidtest.model.PostItemView
import javax.inject.Singleton

@Singleton
class PostAdapter() : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<PostItemView>() {
        override fun areItemsTheSame(oldItem: PostItemView, newItem: PostItemView): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostItemView, newItem: PostItemView): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = differ.currentList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(postItemView: PostItemView) {
            binding.titleView.text = postItemView.title
        }
    }

    private var onItemClickListener: ((PostItemView, Boolean) -> Unit)? = null
    fun setOnItemClickListener(listener: (PostItemView, Boolean) -> Unit) {
        onItemClickListener = listener
    }

    private var onContainerClickListener: ((PostItemView) -> Unit)? = null
    fun setOnContainerClickListener(listener: (PostItemView) -> Unit) {
        onContainerClickListener = listener
    }
}