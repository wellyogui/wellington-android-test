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
            with(binding) {
                titleView.text = postItemView.title
                favoriteView.setOnClickListener {
                    onFavoriteItemClickListener?.let {
                        it(postItemView, postItemView.isFavorite.not())
                    }
                }

                deleteView.setOnClickListener {
                    onDeleteItemClickListener?.let {
                        it(postItemView)
                    }
                }

                itemViewGroup.setOnClickListener {
                    onItemClickListener?.let {
                        it(postItemView)
                    }
                }
            }
        }
    }

    private var onFavoriteItemClickListener: ((PostItemView, Boolean) -> Unit)? = null
    fun setOnFavoriteItemClickListener(listener: (PostItemView, Boolean) -> Unit) {
        onFavoriteItemClickListener = listener
    }

    private var onDeleteItemClickListener: ((PostItemView) -> Unit)? = null

    fun setOnDeleteItemClickListener(listener: (PostItemView) -> Unit) {
        onDeleteItemClickListener = listener
    }

    private var onItemClickListener: ((PostItemView) -> Unit)? = null
    fun setOnItemClickListener(listener: (PostItemView) -> Unit) {
        onItemClickListener = listener
    }
}