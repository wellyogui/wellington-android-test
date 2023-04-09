package com.wellignton.androidtest.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wellignton.androidtest.data.model.Comment
import com.wellignton.androidtest.data.model.Post
import com.wellignton.androidtest.databinding.FragmentPostDetailBinding
import com.wellignton.androidtest.ui.adapter.CommentAdapter
import com.wellignton.androidtest.ui.adapter.PostAdapter
import com.wellignton.androidtest.ui.viewmodel.PostDetailViewModel
import com.wellignton.androidtest.util.Resource
import com.wellignton.androidtest.util.gone
import com.wellignton.androidtest.util.view.toComment
import com.wellignton.androidtest.util.view.toCommentEntity
import com.wellignton.androidtest.util.view.toCommentItemView
import com.wellignton.androidtest.util.view.toPost
import com.wellignton.androidtest.util.view.toPostItemView
import com.wellignton.androidtest.util.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
@AndroidEntryPoint
class PostDetailFragment : Fragment() {

    companion object {
        const val EXTRA_POST_ID = "EXTRA_POST_ID"

        fun newInstance(postId: Int): PostDetailFragment {
            val fragment = PostDetailFragment()
            fragment.arguments = Bundle().apply {
                putInt(EXTRA_POST_ID, postId)
            }
            return fragment
        }
    }

    @Inject
    lateinit var commentAdapter: CommentAdapter

    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostDetailViewModel by viewModels()

    private var postId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        initData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPostDetail(postId)

        initView()
        setupObservers()
    }

    private fun initView() {
        with(binding) {
            commentsView.adapter = commentAdapter
            errorView.action = { viewModel.getPostDetail(postId) }
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            postDetailLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        viewModel.getLocalPost(postId)
                    }
                    is Resource.Loading -> {
                        with(binding) {
                            if (it.isLoading) {
                                loadingView.visible()
                            } else {
                                loadingView.gone()
                            }
                        }
                    }
                    is Resource.Success -> {
                        setupDetail(it.data.first)
                        setupComments(it.data.second)
                        it.data.second.map { comment ->
                            viewModel.saveComment(comment.toCommentEntity())
                        }
                    }
                }
            }

            localPostDetailLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {
                        binding.errorView.visible()
                    }
                    is Resource.Loading -> {
                        with(binding) {
                            if (it.isLoading) {
                                loadingView.visible()
                            } else {
                                loadingView.gone()
                            }
                        }
                    }
                    is Resource.Success -> {
                        setupDetail(it.data.first.toPost())
                        setupComments(it.data.second.map { comment -> comment.toComment() })
                    }
                }
            }
        }
    }

    private fun setupDetail(post: Post) {
        with(binding) {
            postDetailViewGroup.visible()
            postTitleView.text = post.title
            postDescriptionView.text = post.body
            authorInfoView.text = "Author: ${post.userId}"
        }
    }

    private fun setupComments(comments: List<Comment>) {
        val commentItemView = comments.map { comment ->
            comment.toCommentItemView()
        }
        commentAdapter.differ.submitList(commentItemView)
    }

    private fun initData() {
        arguments?.getInt(EXTRA_POST_ID)?.let {
            postId = it
        } ?: run {
            Log.e("Bundle %s is required and was not found", EXTRA_POST_ID)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}