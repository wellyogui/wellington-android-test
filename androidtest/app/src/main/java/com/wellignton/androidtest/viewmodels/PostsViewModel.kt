package com.wellignton.androidtest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wellignton.androidtest.model.Post
import com.wellignton.androidtest.service.AppRepository
import com.wellignton.androidtest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(private val repository: AppRepository): ViewModel() {

    private val _data = MutableLiveData<Resource<List<Post>>>()
    val postsLiveData: MutableLiveData<Resource<List<Post>>> = _data
    private val disposables: ArrayList<Disposable> = arrayListOf()

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

    override fun onCleared() {
        super.onCleared()
        disposables.forEach {
            it.dispose()
        }
    }
}