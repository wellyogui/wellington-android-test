package com.wellignton.androidtest.data.remote.post

import com.wellignton.androidtest.data.model.Comment
import com.wellignton.androidtest.data.model.Post
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by well_ on 08/04/2023 for Android test.
 */
interface PostService {

    @GET("/posts")
    fun getPosts(): Single<List<Post>>

    @GET("/posts/{id}")
    fun getPost(@Path("id") id: String): Single<Post>

    @GET("/posts/{id}/comments")
    fun getPostComments(@Path("id") id: String): Single<List<Comment>>
}