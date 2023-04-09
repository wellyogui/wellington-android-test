package com.wellignton.androidtest

import com.nhaarman.mockitokotlin2.any
import com.wellignton.androidtest.data.local.PostDao
import com.wellignton.androidtest.data.model.Post
import com.wellignton.androidtest.data.model.PostEntity
import com.wellignton.androidtest.data.remote.post.PostService
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
@RunWith(MockitoJUnitRunner::class)
class PostsFragmentTest {
    @Mock
    private lateinit var mockApi: PostService

    @Mock
    private lateinit var mockPostDao: PostDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `getPosts returns a list of posts`() {
        val posts = listOf(Post(1, 1, "Test title", "Test body"))

        Mockito.`when`(mockApi.getPosts()).thenReturn(Single.just(posts))

        mockApi.getPosts().test()
            .assertValue(posts)
    }

    @Test
    fun `test getPosts() fails with an error`() {
        val expectedError = Throwable("Test error")
        `when`(mockApi.getPosts()).thenReturn(Single.error(expectedError))

        val testObserver = mockApi.getPosts().test()

        testObserver.assertError(expectedError)
    }

    @Test
    fun `getPosts returns a list of PostEntity objects`() {
        val posts = listOf(
            PostEntity(1, 1, "Test title", "Test body", false),
            PostEntity(2, 1, "Another title", "Another body", false)
        )

        `when`(mockPostDao.getPosts()).thenReturn(Single.just(posts))

        val testObserver = mockPostDao.getPosts().test()
        testObserver.assertValue(posts)

        verify(mockPostDao).getPosts()
    }

    @Test
    fun `getPosts fails with an exception`() {
        val exception = Exception("Test exception")
        `when`(mockPostDao.getPosts()).thenReturn(Single.error(exception))

        val testObserver = mockPostDao.getPosts().test()
        testObserver.assertError(exception)

        verify(mockPostDao).getPosts()
    }

    @Test
    fun `insertPost completes successfully`() {
        `when`(mockPostDao.insertPost(any())).thenReturn(Completable.complete())

        val post = PostEntity(1, 1, "Test title", "Test body", false)
        val testObserver = mockPostDao.insertPost(post).test()
        testObserver.assertComplete()

        verify(mockPostDao).insertPost(post)
    }

    @Test
    fun `insertPost fails with an exception`() {
        val exception = Exception("Test exception")
        `when`(mockPostDao.insertPost(any())).thenReturn(Completable.error(exception))

        val post = PostEntity(1, 1, "Test title", "Test body", false)
        val testObserver = mockPostDao.insertPost(post).test()
        testObserver.assertError(exception)

        verify(mockPostDao).insertPost(post)
    }

    @Test
    fun `deletePost completes successfully`() {
        `when`(mockPostDao.deletePost(any())).thenReturn(Completable.complete())

        val post = PostEntity(1, 1, "Test title", "Test body", false)
        val testObserver = mockPostDao.deletePost(post).test()
        testObserver.assertComplete()

        verify(mockPostDao).deletePost(post)
    }

    @Test
    fun `deletePost fails with an exception`() {
        val exception = Exception("Test exception")
        `when`(mockPostDao.deletePost(any())).thenReturn(Completable.error(exception))

        val post = PostEntity(1, 1, "Test title", "Test body", false)
        val testObserver = mockPostDao.deletePost(post).test()
        testObserver.assertError(exception)

        verify(mockPostDao).deletePost(post)
    }

    @Test
    fun `deleteNonFavoritePosts completes successfully`() {
        `when`(mockPostDao.deleteNonFavoritePosts()).thenReturn(Completable.complete())

        val testObserver = mockPostDao.deleteNonFavoritePosts().test()
        testObserver.assertComplete()

        verify(mockPostDao).deleteNonFavoritePosts()
    }

    @Test
    fun `deleteNonFavoritePosts fails with an exception`() {
        val exception = Exception("Test exception")
        `when`(mockPostDao.deleteNonFavoritePosts()).thenReturn(Completable.error(exception))

        val testObserver = mockPostDao.deleteNonFavoritePosts().test()
        testObserver.assertError(exception)

        verify(mockPostDao).deleteNonFavoritePosts()
    }

    @Test
    fun `deletePosts completes successfully`() {
        `when`(mockPostDao.deletePosts()).thenReturn(Completable.complete())

        val testObserver = mockPostDao.deletePosts().test()
        testObserver.assertComplete()

        verify(mockPostDao).deletePosts()
    }

    @Test
    fun `deletePosts fails with an exception`() {
        val exception = Exception("Test exception")
        `when`(mockPostDao.deletePosts()).thenReturn(Completable.error(exception))

        val testObserver = mockPostDao.deletePosts().test()
        testObserver.assertError(exception)

        verify(mockPostDao).deletePosts()
    }

    @Test
    fun `updatePostFavorite completes successfully`() {
        `when`(mockPostDao.updatePostFavorite(1, true)).thenReturn(Completable.complete())

        val testObserver = mockPostDao.updatePostFavorite(1, true).test()
        testObserver.assertComplete()

        verify(mockPostDao).updatePostFavorite(1, true)
    }

    @Test
    fun `updatePostFavorite fails with an exception`() {
        val exception = Exception("Test exception")
        `when`(mockPostDao.updatePostFavorite(1, true)).thenReturn(Completable.error(exception))

        val testObserver = mockPostDao.updatePostFavorite(1, true).test()
        testObserver.assertError(exception)

        verify(mockPostDao).updatePostFavorite(1, true)
    }
}