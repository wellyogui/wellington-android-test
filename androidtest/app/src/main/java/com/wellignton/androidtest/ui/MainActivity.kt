package com.wellignton.androidtest.ui

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import com.wellignton.androidtest.R
import com.wellignton.androidtest.databinding.ActivityMainBinding
import com.wellignton.androidtest.ui.fragment.PostDetailFragment
import com.wellignton.androidtest.ui.fragment.PostsFragment
import com.wellignton.androidtest.util.view.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(PostsFragment.newInstance().apply {
            setOnPostClickListener {
                openPostDetail(it)
            }
        }, R.id.mainRootView, true)
    }

    private fun openPostDetail(postId: Int) {
        replaceFragment(PostDetailFragment.newInstance(postId), R.id.mainRootView, false)
    }
}