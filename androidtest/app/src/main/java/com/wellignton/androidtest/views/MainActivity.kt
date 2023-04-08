package com.wellignton.androidtest.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wellignton.androidtest.R
import com.wellignton.androidtest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainRootView, PostsFragment())
            .commit()
    }
}