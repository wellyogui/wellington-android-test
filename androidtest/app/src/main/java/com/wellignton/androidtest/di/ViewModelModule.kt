package com.wellignton.androidtest.di

import com.wellignton.androidtest.data.remote.AppRepository
import com.wellignton.androidtest.data.remote.post.PostService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by well_ on 08/04/2023 for Android test.
 */
@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelMovieModule {
    @Provides
    @ViewModelScoped
    fun provideRepo(postService: PostService) = AppRepository(postService)
}
