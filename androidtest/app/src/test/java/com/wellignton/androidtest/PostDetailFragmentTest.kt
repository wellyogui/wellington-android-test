package com.wellignton.androidtest

import com.nhaarman.mockitokotlin2.any
import com.wellignton.androidtest.data.local.CommentDao
import com.wellignton.androidtest.data.local.PostDao
import com.wellignton.androidtest.data.model.Comment
import com.wellignton.androidtest.data.model.CommentEntity
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
class PostDetailFragmentTest {
    @Mock
    private lateinit var mockApi: PostService

    @Mock
    private lateinit var mockPostDao: PostDao

    @Mock
    private lateinit var mockCommentDao: CommentDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `getPostById return a post detail`() {
        val postId = 1
        val post = Post(id = postId, title = "test title", body = "test body", userId = 1)
        `when`(mockApi.getPost(postId.toString())).thenReturn(Single.just(post))

        val testObserver = mockApi.getPost(postId.toString()).test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValue(post)

        verify(mockApi).getPost(postId.toString())
    }

    @Test
    fun `getPostById fail with a throwable`() {
        val postId = 1
        val error = Throwable("test error")
        `when`(mockApi.getPost(postId.toString())).thenReturn(Single.error(error))

        val testObserver = mockApi.getPost(postId.toString()).test()

        testObserver.assertNotComplete()
        testObserver.assertError(error)

        verify(mockApi).getPost(postId.toString())
    }

    @Test
    fun `getPostComments returns a list of comments`() {
        val postId = 1
        val comments = listOf(
            Comment(
                id = 1,
                postId = postId,
                name = "Comment 1",
                email = "comment1@example.com",
                body = "This is the body of comment 1"
            ),
            Comment(
                id = 2,
                postId = postId,
                name = "Comment 2",
                email = "comment2@example.com",
                body = "This is the body of comment 2"
            )
        )
        `when`(mockApi.getPostComments(postId.toString())).thenReturn(Single.just(comments))

        val testObserver = mockApi.getPostComments(postId.toString()).test()

        testObserver.assertNoErrors()
        testObserver.assertValue(comments)

        verify(mockApi).getPostComments(postId.toString())
    }

    @Test
    fun `getPostComments fail with a throwable`() {
        val postId = 1
        val errorMsg = "Error getting post comments"
        `when`(mockApi.getPostComments(postId.toString())).thenReturn(Single.error(Throwable(errorMsg)))

        val testObserver = mockApi.getPostComments(postId.toString()).test()

        testObserver.assertError { it.message == errorMsg }

        verify(mockApi).getPostComments(postId.toString())
    }

    @Test
    fun `getComments returns a list of comments`() {
        val comments = listOf(
            CommentEntity(1, 1, "John", "John@mail.com","Comment 1"),
            CommentEntity(2, 1, "Mary", "Mary@mail.com","Comment 2")
        )

        `when`(mockCommentDao.getComments())
            .thenReturn(Single.just(comments))

        val testObserver = mockCommentDao.getComments().test()

        testObserver.assertNoErrors()
        testObserver.assertValue(comments)
        testObserver.assertComplete()

        verify(mockCommentDao).getComments()
    }

    @Test
    fun `getComments fails with an exception`() {
        val exception = Exception("Test exception")
        `when`(mockCommentDao.getComments()).thenReturn(Single.error(exception))

        val testObserver = mockCommentDao.getComments().test()
        testObserver.assertError(exception)

        verify(mockCommentDao).getComments()
    }

    @Test
    fun `insertComment completes successfully`() {
        `when`(mockCommentDao.insertComment(any())).thenReturn(Completable.complete())

        val comment = CommentEntity(1, 1, "John", "John@mail.com","Comment")
        val testObserver = mockCommentDao.insertComment(comment).test()
        testObserver.assertComplete()

        verify(mockCommentDao).insertComment(comment)
    }

    @Test
    fun `insertComment fails with an exception`() {
        val exception = Exception("Test exception")
        `when`(mockCommentDao.insertComment(any())).thenReturn(Completable.error(exception))

        val comment = CommentEntity(1, 1, "John", "John@mail.com","Comment")
        val testObserver = mockCommentDao.insertComment(comment).test()
        testObserver.assertError(exception)

        verify(mockCommentDao).insertComment(comment)
    }

    @Test
    fun `getPostById return a post`() {
        val postId = 1
        val postEntity = PostEntity(postId, 1, "title", "body", false)
        `when`(mockPostDao.getPostById(postId)).thenReturn(Single.just(postEntity))

        val testObserver = mockPostDao.getPostById(postId).test()

        testObserver.assertNoErrors()
        testObserver.assertValue(postEntity)

        verify(mockPostDao).getPostById(postId)
    }

    @Test
    fun `getPostById fails with a throwable`() {
        val postId = 1
        val errorMsg = "Error getting post with id $postId"
        `when`(mockPostDao.getPostById(postId)).thenReturn(Single.error(Throwable(errorMsg)))

        val testObserver = mockPostDao.getPostById(postId).test()

        testObserver.assertErrorMessage(errorMsg)

        verify(mockPostDao).getPostById(postId)
    }
}
