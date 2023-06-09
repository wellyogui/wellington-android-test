package com.wellignton.androidtest.di

import android.content.Context
import com.wellignton.androidtest.ui.adapter.CommentAdapter
import com.wellignton.androidtest.ui.adapter.PostAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providePostAdapter(@ApplicationContext context: Context): PostAdapter = PostAdapter(context)

    @Provides
    @Singleton
    fun provideCommentAdapter(): CommentAdapter = CommentAdapter()

}