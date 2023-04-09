package com.wellignton.androidtest.data.model

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
data class PostItemView(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val isFavorite: Boolean
)