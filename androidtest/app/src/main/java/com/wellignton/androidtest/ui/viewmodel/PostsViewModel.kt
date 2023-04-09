package com.wellignton.androidtest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wellignton.androidtest.data.local.LocalPostRepository
import com.wellignton.androidtest.data.model.Post
import com.wellignton.androidtest.data.model.PostEntity
import com.wellignton.androidtest.data.remote.AppRepository
import com.wellignton.androidtest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: AppRepository,
    private val localPostRepository: LocalPostRepository
) : ViewModel() {
    private val disposables: ArrayList<Disposable> = arrayListOf()

    val postsLiveData: MutableLiveData<Resource<List<Post>>> =
        MutableLiveData<Resource<List<Post>>>()
    val localPostsLiveData: MutableLiveData<Resource<List<PostEntity>>> =
        MutableLiveData<Resource<List<PostEntity>>>()
    val deleteAllPostsLiveData: MutableLiveData<Resource<Unit>> = MutableLiveData<Resource<Unit>>()
    val insetLocalPostsLiveData: MutableLiveData<Resource<Unit>> = MutableLiveData<Resource<Unit>>()
    val favoritePostLiveData: MutableLiveData<Resource<Pair<Int, Boolean>>> =
        MutableLiveData<Resource<Pair<Int, Boolean>>>()
    val deleteNonFavoritePosts: MutableLiveData<Resource<Unit>> = MutableLiveData<Resource<Unit>>()
    val deletePost: MutableLiveData<Resource<Unit>> = MutableLiveData<Resource<Unit>>()

    fun getPostFromLocal() {
        localPostRepository.getLocalPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                localPostsLiveData.value = Resource.Loading(true)
            }
            .doFinally {
                localPostsLiveData.value = Resource.Loading(false)
            }
            .subscribe({
                localPostsLiveData.value = Resource.Success(it)
            }, {
                localPostsLiveData.value = Resource.Error(it) { getPosts() }
            }).run {
                disposables.add(this)
            }
    }

    fun insetPost(post: PostEntity) {
        localPostRepository.addLocalPost(
            PostEntity(
                post.id,
                post.userId,
                post.title,
                post.body,
                false
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                insetLocalPostsLiveData.value = Resource.Loading(true)
            }
            .doFinally {
                insetLocalPostsLiveData.value = Resource.Loading(false)
            }
            .subscribe({
                insetLocalPostsLiveData.value = Resource.Success(Unit)
            }, {
                insetLocalPostsLiveData.value = Resource.Error(it) { insetPost(post) }
            }).run {
                disposables.add(this)
            }
    }

    fun getPosts() {
        repository.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                postsLiveData.value = Resource.Loading(true)
            }
            .doFinally {
                postsLiveData.value = Resource.Loading(false)
            }
            .subscribe({
                postsLiveData.value = Resource.Success(it)
            }, {
                postsLiveData.value = Resource.Error(it) { getPosts() }
            }).run {
                disposables.add(this)
            }
    }

    fun deleteAllPosts() {
        localPostRepository.deleteAllPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                deleteAllPostsLiveData.value = Resource.Success(Unit)
            }, {
                deleteAllPostsLiveData.value = Resource.Error(it) { deleteAllPosts() }
            }).run {
                disposables.add(this)
            }
    }

    fun favoritePost(postId: Int, isFavorite: Boolean, position: Int) {
        localPostRepository.favoritePost(postId, isFavorite)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                favoritePostLiveData.value = Resource.Loading(true)
            }
            .doFinally {
                favoritePostLiveData.value = Resource.Loading(false)
            }.subscribe({
                favoritePostLiveData.value = Resource.Success(Pair(position, isFavorite))
            }, {
                favoritePostLiveData.value =
                    Resource.Error(it) { favoritePost(postId, isFavorite, position) }
            }).run {
                disposables.add(this)
            }
    }

    fun deletePost(post: PostEntity) {
        localPostRepository.deleteLocalPost(post)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                deletePost.value = Resource.Loading(true)
            }
            .doFinally {
                deletePost.value = Resource.Loading(false)
            }.subscribe({
                deletePost.value = Resource.Success(Unit)
            }, {
                deletePost.value = Resource.Error(it) { deletePost(post) }
            }).run {
                disposables.add(this)
            }
    }

    fun deleteNonFavoritePosts() {
        localPostRepository.deleteNonFavoritePosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                deleteNonFavoritePosts.value = Resource.Loading(true)
            }
            .doFinally {
                deleteNonFavoritePosts.value = Resource.Loading(false)
            }.subscribe({
                deleteNonFavoritePosts.value = Resource.Success(Unit)
            }, {
                deleteNonFavoritePosts.value = Resource.Error(it) { deleteNonFavoritePosts() }
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