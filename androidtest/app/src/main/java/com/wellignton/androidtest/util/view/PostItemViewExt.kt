package com.wellignton.androidtest.util.view

import com.wellignton.androidtest.data.model.PostEntity
import com.wellignton.androidtest.data.model.PostItemView

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
fun PostItemView.toPostEntity(): PostEntity {
    return PostEntity(id, userId, title, body, isFavorite)
}