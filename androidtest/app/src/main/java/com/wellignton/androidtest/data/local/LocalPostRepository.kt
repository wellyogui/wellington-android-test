package com.wellignton.androidtest.data.local

import com.wellignton.androidtest.data.model.PostEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
class LocalPostRepository @Inject constructor(private val postDao: PostDao) {

    fun getLocalPosts(): Single<List<PostEntity>> {
        return postDao.getPosts()
    }

    fun getPostById(postId: Int): Single<PostEntity> {
        return postDao.getPostById(postId)
    }

    fun addLocalPost(postEntity: PostEntity): Completable {
        return postDao.insertPost(postEntity)
    }

    fun deleteLocalPost(postEntity: PostEntity): Completable {
        return postDao.deletePost(postEntity)
    }

    fun favoritePost(postId: Int, isFavorite: Boolean): Completable {
        return postDao.updatePostFavorite(postId, isFavorite)
    }

    fun deleteNonFavoritePosts(): Completable {
        return postDao.deleteNonFavoritePosts()
    }

    fun deleteAllPosts(): Completable {
        return postDao.deletePosts()
    }
}