package com.wellignton.androidtest.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.wellignton.androidtest.R
import com.wellignton.androidtest.data.model.PostItemView
import com.wellignton.androidtest.databinding.FragmentPostsBinding
import com.wellignton.androidtest.ui.adapter.PostAdapter
import com.wellignton.androidtest.ui.viewmodel.PostsViewModel
import com.wellignton.androidtest.util.Resource
import com.wellignton.androidtest.util.gone
import com.wellignton.androidtest.util.showSnackBar
import com.wellignton.androidtest.util.view.toPostEntity
import com.wellignton.androidtest.util.view.toPostItemView
import com.wellignton.androidtest.util.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by well_ on 08/04/2023 for Android test.
 */

@AndroidEntryPoint
class PostsFragment : Fragment() {

    companion object {
        fun newInstance(): PostsFragment {
            return PostsFragment()
        }
    }

    @Inject
    lateinit var postAdapter: PostAdapter

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostsViewModel by viewModels()

    val builder by lazy {
        androidx.appcompat.app.AlertDialog.Builder(
            requireActivity(),
            R.style.AlertDialogThemeWarning
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPostFromLocal()

        initView()
        setupObservers()
    }

    private fun initView() {
        binding.postsView.adapter = postAdapter.apply {
            setOnDeleteItemClickListener { postItemView, position ->
                dialog("Are you sure you want delete this post?") {
                    deletePost(
                        postItemView,
                        position
                    )
                }
            }

            setOnFavoriteItemClickListener { postItemView, isFavorite, position ->
                viewModel.favoritePost(postItemView.id, isFavorite, position)
            }

            setOnItemClickListener { postItemView ->
                onPostClickListener?.invoke(postItemView.id)
            }
        }

        binding.deleteNonFavoritePostView.setOnClickListener {
            dialog("Are you sure you want delete non favorites posts?") { deleteNonPost() }
        }

        binding.reloadItemsView.setOnClickListener {
            dialog("Are you sure you want to reload items?") { reloadItems() }
        }
    }

    private fun reloadItems() {
        postAdapter.removeAll()
        viewModel.deleteAllPosts()
    }

    private fun deletePost(postItemView: PostItemView, position: Int) {
        postAdapter.removeAt(position)
        viewModel.deletePost(postItemView.toPostEntity())
    }

    private fun deleteNonPost() {
        postAdapter.removeNonFavoritePosts()
        viewModel.deleteNonFavoritePosts()
    }

    private fun setupObservers() {
        with(viewModel) {
            localPostsLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setupError(it.action)
                    is Resource.Loading -> {
                        setupLoading(it.isLoading)
                    }
                    is Resource.Success -> {
                        with(binding) {
                            postsView.visible()
                            errorView.gone()
                        }
                        if (it.data.isEmpty()) {
                            viewModel.getPosts()
                        }
                        val postItemsView = it.data.map { post ->
                            post.toPostItemView()
                        }
                        postAdapter.differ.submitList(postItemsView)
                    }
                }
            }

            postsLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setupError(it.action)
                    is Resource.Loading -> setupLoading(it.isLoading)
                    is Resource.Success -> {
                        with(binding) {
                            postsView.visible()
                            errorView.gone()
                        }
                        val postItemsView = it.data.map { post ->
                            viewModel.insetPost(post.toPostEntity())
                            post.toPostItemView()
                        }
                        postAdapter.differ.submitList(postItemsView)
                    }
                }
            }

            favoritePostLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        binding.rootView.showSnackBar(
                            "Couldn't favorite",
                            Snackbar.LENGTH_INDEFINITE,
                            "Try again",
                            it.action
                        )
                    }
                    is Resource.Loading -> {
                        //Do nothing
                    }
                    is Resource.Success -> {
                        postAdapter.updateFavorite(it.data.first, it.data.second)
                        binding.rootView.showSnackBar(
                            "Successfully favorited",
                            Snackbar.LENGTH_LONG
                        )
                    }
                }
            }

            deleteNonFavoritePosts.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        binding.rootView.showSnackBar(
                            "Couldn't delete non favorite posts",
                            Snackbar.LENGTH_INDEFINITE,
                            "Try again",
                            it.action
                        )
                    }
                    is Resource.Loading -> {
                        //Do nothing
                    }
                    is Resource.Success -> {
                        binding.rootView.showSnackBar(
                            "Non favorite posts deleted",
                            Snackbar.LENGTH_LONG
                        )
                    }
                }
            }

            deletePost.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        binding.rootView.showSnackBar(
                            "Couldn't delete post",
                            Snackbar.LENGTH_INDEFINITE,
                            "Try again",
                            it.action
                        )
                    }
                    is Resource.Loading -> {
                        //Do nothing
                    }
                    is Resource.Success -> {
                        binding.rootView.showSnackBar("Post deleted", Snackbar.LENGTH_LONG)
                    }
                }
            }

            deleteAllPostsLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setupError(it.action)
                    is Resource.Loading -> {
                        setupLoading(it.isLoading)
                    }
                    is Resource.Success -> {
                        binding.errorView.gone()
                        viewModel.getPosts()
                    }
                }
            }

            insetLocalPostsLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> setupError(it.action)
                    is Resource.Loading -> {
                        //Do nothing
                    }
                    is Resource.Success -> {
                        //Do nothing
                    }
                }
            }
        }
    }

    private fun setupLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                loadingView.visible()
            } else {
                loadingView.gone()
            }
        }
    }

    private fun setupError(callback: () -> Unit) {
        with(binding) {
            postsView.gone()
            errorView.apply {
                visible()
                action = callback
            }
        }
    }

    private fun dialog(message: String, callback: (() -> Unit)) {
        builder.setMessage(message)
            .setPositiveButton("Yes") { _, _ ->
                callback.invoke()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create()
        builder.show()
    }

    private var onPostClickListener: ((Int) -> Unit)? = null
    fun setOnPostClickListener(listener: (Int) -> Unit) {
        onPostClickListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}