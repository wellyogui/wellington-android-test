package com.wellignton.androidtest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
@Entity(tableName = "post")
data class PostEntity(@PrimaryKey val id: Int,
                      val userId: Int,
                      val title: String,
                      val body: String,
                      val isFavorite: Boolean)