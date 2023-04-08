package com.wellignton.androidtest.service

import com.wellignton.androidtest.model.Post
import com.wellignton.androidtest.service.post.PostService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by well_ on 08/04/2023 for Android test.
 */
class AppRepository @Inject constructor(private val postService: PostService) {

    fun getPosts(): Single<List<Post>> {
        return postService.getPosts()
    }
}