package com.wellignton.androidtest.module

import com.wellignton.androidtest.views.adapter.PostAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAdapter(): PostAdapter = PostAdapter()
}