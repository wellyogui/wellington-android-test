package com.wellignton.androidtest.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wellignton.androidtest.R
import com.wellignton.androidtest.data.model.PostItemView
import com.wellignton.androidtest.databinding.ItemPostBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostAdapter @Inject constructor(@ApplicationContext val context: Context) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

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
                favoriteView.setImageDrawable(setupFavorite(postItemView.isFavorite.not()))

                favoriteView.setOnClickListener {
                    onFavoriteItemClickListener?.let {
                        it(postItemView, postItemView.isFavorite.not())
                    }
                }

                deleteView.setOnClickListener {
                    onDeleteItemClickListener?.let {
                        it(postItemView, differ.currentList.indexOf(postItemView))
                    }
                }

                itemViewGroup.setOnClickListener {
                    onItemClickListener?.let {
                        it(postItemView)
                    }
                }
            }
        }

        private fun setupFavorite(isFavorite: Boolean): Drawable? {
            return if (isFavorite) {
                AppCompatResources.getDrawable(context, R.drawable.ic_favorite_border)
            } else {
                AppCompatResources.getDrawable(context, R.drawable.ic_favorite)
            }
        }
    }

    fun removeAt(position: Int) {
        val list = differ.currentList.toMutableList()
        list.removeAt(position)
        differ.submitList(list)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, differ.currentList.size)
    }

    fun removeAll() {
        val list = differ.currentList.toMutableList()
        if (list.isEmpty()) {
            return
        }
        list.clear()
        differ.submitList(list)
        notifyDataSetChanged()
    }

    fun updateFavorite(position: Int, isFavorite: Boolean) {
        differ.currentList[position].isFavorite = isFavorite
        notifyItemChanged(position)
        notifyItemRangeChanged(position, differ.currentList.size)
    }

    fun removeNonFavoritePosts() {
        val list = differ.currentList.filter { it.isFavorite }.toMutableList()
        differ.submitList(list)
        notifyDataSetChanged()
    }

    private var onFavoriteItemClickListener: ((PostItemView, Boolean) -> Unit)? = null
    fun setOnFavoriteItemClickListener(listener: (PostItemView, Boolean) -> Unit) {
        onFavoriteItemClickListener = listener
    }

    private var onDeleteItemClickListener: ((PostItemView, Int) -> Unit)? = null
    fun setOnDeleteItemClickListener(listener: (PostItemView, Int) -> Unit) {
        onDeleteItemClickListener = listener
    }

    private var onItemClickListener: ((PostItemView) -> Unit)? = null
    fun setOnItemClickListener(listener: (PostItemView) -> Unit) {
        onItemClickListener = listener
    }
}