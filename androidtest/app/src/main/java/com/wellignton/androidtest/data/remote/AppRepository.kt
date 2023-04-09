package com.wellignton.androidtest.data.remote

import com.wellignton.androidtest.data.model.Comment
import com.wellignton.androidtest.data.model.Post
import com.wellignton.androidtest.data.remote.post.PostService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by well_ on 08/04/2023 for Android test.
 */
class AppRepository @Inject constructor(private val postService: PostService) {

    fun getPosts(): Single<List<Post>> {
        return postService.getPosts()
    }

    fun getPost(postId: String): Single<Post> {
        return postService.getPost(postId)
    }

    fun getComments(postId: String): Single<List<Comment>> {
        return postService.getPostComments(postId)
    }
}