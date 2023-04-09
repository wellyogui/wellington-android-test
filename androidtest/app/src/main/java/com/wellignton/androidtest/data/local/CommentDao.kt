package com.wellignton.androidtest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wellignton.androidtest.data.model.CommentEntity
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by well_ on 09/04/2023 for Android test.
 */

@Dao
interface CommentDao {
    @Query("SELECT * FROM comment")
    fun getComments(): Single<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(commentEntity: CommentEntity): Completable
}