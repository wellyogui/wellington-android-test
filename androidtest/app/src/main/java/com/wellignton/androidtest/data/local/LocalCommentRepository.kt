package com.wellignton.androidtest.data.local

import com.wellignton.androidtest.data.model.CommentEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
class LocalCommentRepository @Inject constructor(private val commentDao: CommentDao) {

    fun getComments(): Single<List<CommentEntity>> {
        return commentDao.getComments()
    }

    fun addComment(commentEntity: CommentEntity): Completable {
        return commentDao.insertComment(commentEntity)
    }
}