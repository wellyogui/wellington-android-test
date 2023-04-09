package com.wellignton.androidtest.util.view

import com.wellignton.androidtest.data.model.Comment
import com.wellignton.androidtest.data.model.CommentEntity

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
fun CommentEntity.toComment() : Comment {
    return Comment(id, postId, name, email, body)
}