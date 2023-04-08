package com.wellignton.androidtest.service.post

import com.wellignton.androidtest.model.Comment
import com.wellignton.androidtest.model.Post
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by well_ on 08/04/2023 for Android test.
 */
interface PostService {

    @GET("/posts")
    fun getPosts(): Single<List<Post>>

    @GET("/post/{id}")
    fun getPost(@Query("id") id: String): Single<Post>

    @GET("/post/{id}/comments")
    fun getPostComments(@Query("id") id: String) : Single<List<Comment>>
}