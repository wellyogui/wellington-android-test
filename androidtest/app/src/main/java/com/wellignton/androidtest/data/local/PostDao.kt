package com.wellignton.androidtest.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wellignton.androidtest.data.model.PostEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getPosts(): Single<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: PostEntity): Completable

    @Query("SELECT * FROM post WHERE id = :postId")
    fun getPostById(postId: Int) : Single<PostEntity>

    @Delete
    fun deletePost(post: PostEntity): Completable

    @Query("DELETE FROM post WHERE isFavorite = 0")
    fun deleteNonFavoritePosts(): Completable

    @Query("DELETE FROM post")
    fun deletePosts(): Completable

    @Query("UPDATE post SET isFavorite = :isFavorite WHERE id = :postId")
    fun updatePostFavorite(postId: Int, isFavorite: Boolean): Completable
}