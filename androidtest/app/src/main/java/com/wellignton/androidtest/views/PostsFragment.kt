package com.wellignton.androidtest.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.wellignton.androidtest.databinding.FragmentPostsBinding
import com.wellignton.androidtest.model.PostItemView
import com.wellignton.androidtest.util.Resource
import com.wellignton.androidtest.util.gone
import com.wellignton.androidtest.util.visible
import com.wellignton.androidtest.viewmodels.PostsViewModel
import com.wellignton.androidtest.views.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by well_ on 08/04/2023 for Android test.
 */

@AndroidEntryPoint
class PostsFragment: Fragment() {

    @Inject
    lateinit var postAdapter: PostAdapter

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostsViewModel by viewModels()

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
        viewModel.getPosts()

        viewModel.postsLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Error -> {
                    //TODO()
                }
                is Resource.Loading -> {
                    with(binding) {
                        if(it.isLoading) {
                            loadingView.visible()
                        } else {
                            loadingView.gone()
                        }
                    }
                }
                is Resource.Success -> {
                    with(binding) {
                        val postItemsView = it.data.map {post ->
                            PostItemView(post.id, post.title, false)
                        }
                        postAdapter.differ.submitList(postItemsView)
                        postsView.adapter = postAdapter
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}