package com.wellignton.androidtest.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wellignton.androidtest.databinding.ItemPostBinding
import com.wellignton.androidtest.model.Post
import javax.inject.Singleton

@Singleton
class PostAdapter() : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {


    // Helper for computing the difference between two lists
    private val callback = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
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
        val restaurant = differ.currentList[position]
        holder.bind(restaurant)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(Post: Post) {
            binding.titleView.text = Post.title
        }
    }

    private var onItemClickListener: ((Post, Boolean) -> Unit)? = null
    fun setOnItemClickListener(listener: (Post, Boolean) -> Unit) {
        onItemClickListener = listener
    }

    private var onContainerClickListener: ((Post) -> Unit)? = null
    fun setOnContainerClickListener(listener: (Post) -> Unit) {
        onContainerClickListener = listener
    }
}