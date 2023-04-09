package com.wellignton.androidtest.util.view

import com.wellignton.androidtest.data.model.Comment
import com.wellignton.androidtest.data.model.CommentEntity
import com.wellignton.androidtest.data.model.CommentItemView

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
fun Comment.toCommentItemView(): CommentItemView {
    return CommentItemView(id, postId, name, email, body)
}

fun Comment.toCommentEntity(): CommentEntity {
    return CommentEntity(id, postId, name, email, body)
}