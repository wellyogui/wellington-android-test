package com.wellignton.androidtest.util.view

import com.wellignton.androidtest.data.model.Post
import com.wellignton.androidtest.data.model.PostEntity
import com.wellignton.androidtest.data.model.PostItemView

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
fun PostEntity.toPostItemView(): PostItemView {
    return PostItemView(id, userId, title, body, isFavorite)
}

fun PostEntity.toPost(): Post {
    return Post(id, userId, title, body)
}