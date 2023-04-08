package com.wellignton.androidtest.module

import androidx.lifecycle.SavedStateHandle
import com.wellignton.androidtest.service.AppRepository
import com.wellignton.androidtest.service.post.PostService
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
    fun provideRepo(postService: PostService) =
        AppRepository(postService)
}
