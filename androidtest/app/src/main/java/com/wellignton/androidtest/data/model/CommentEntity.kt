package com.wellignton.androidtest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by well_ on 09/04/2023 for Android test.
 */

@Entity(tableName = "comment")
class CommentEntity(
    @PrimaryKey val id: String,
    val postId: String,
    val name: String,
    val email: String,
    val body: String
)