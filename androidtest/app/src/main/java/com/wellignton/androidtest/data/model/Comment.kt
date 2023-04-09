package com.wellignton.androidtest.data.model

/**
 * Created by well_ on 08/04/2023 for Android test.
 */
data class Comment(
    val id: String,
    val postId: String,
    val name: String,
    val email: String,
    val body: String
)