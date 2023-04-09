package com.wellignton.androidtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wellignton.androidtest.data.local.LocalCommentRepository
import com.wellignton.androidtest.data.local.LocalPostRepository
import com.wellignton.androidtest.data.model.Comment
import com.wellignton.androidtest.data.model.CommentEntity
import com.wellignton.androidtest.data.model.Post
import com.wellignton.androidtest.data.model.PostEntity
import com.wellignton.androidtest.data.remote.AppRepository
import com.wellignton.androidtest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by well_ on 09/04/2023 for Android test.
 */

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val repository: AppRepository,
    private val localPostRepository: LocalPostRepository,
    private val localCommentRepository: LocalCommentRepository
) : ViewModel() {
    private val disposables: ArrayList<Disposable> = arrayListOf()

    val postDetailLiveData: MutableLiveData<Resource<Pair<Post, List<Comment>>>> =
        MutableLiveData<Resource<Pair<Post, List<Comment>>>>()

    val localPostDetailLiveData: MutableLiveData<Resource<Pair<PostEntity, List<CommentEntity>>>> =
        MutableLiveData<Resource<Pair<PostEntity, List<CommentEntity>>>>()

    val saveCommentLiveData: MutableLiveData<Resource<Unit>> = MutableLiveData<Resource<Unit>>()

    fun getPostDetail(postId: Int) {
        Single.zip(
            repository.getPost(postId.toString()), getComments(postId)
        ) { post: Post, comments: List<Comment> ->
            Pair(post, comments)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                postDetailLiveData.value = Resource.Loading(true)
            }.doFinally {
                postDetailLiveData.value = Resource.Loading(false)
            }.subscribe({
                postDetailLiveData.value = Resource.Success(it)
            }, {
                postDetailLiveData.value = Resource.Error(it) { getPostDetail(postId) }
            }).run {
                disposables.add(this)
            }
    }

    private fun getComments(postId: Int): Single<List<Comment>> {
        return repository.getComments(postId.toString()).subscribeOn(Schedulers.io())
    }

    fun getLocalPost(postId: Int) {
        Single.zip(
            localPostRepository.getPostById(postId), localCommentRepository.getComments()
        ) { post: PostEntity, comments: List<CommentEntity> ->
            Pair(post, comments)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                localPostDetailLiveData.value = Resource.Loading(true)
            }.doFinally {
                localPostDetailLiveData.value = Resource.Loading(false)
            }.subscribe({
                localPostDetailLiveData.value = Resource.Success(it)
            }, {
                localPostDetailLiveData.value = Resource.Error(it) { getPostDetail(postId) }
            }).run {
                disposables.add(this)
            }
    }

    fun saveComment(commentEntity: CommentEntity) {
        localCommentRepository.addComment(commentEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                saveCommentLiveData.value = Resource.Success(Unit)
            }, {
                saveCommentLiveData.value = Resource.Error(it) { saveComment(commentEntity) }
            }).run {
                disposables.add(this)
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.forEach {
            it.dispose()
        }
    }
}