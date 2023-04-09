package com.wellignton.androidtest.di

import android.content.Context
import androidx.room.Room
import com.wellignton.androidtest.data.local.AppDatabase
import com.wellignton.androidtest.data.local.CommentDao
import com.wellignton.androidtest.data.local.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun providePostDao(appDatabase: AppDatabase): PostDao {
        return appDatabase.postDao()
    }

    @Provides
    fun provideCommentDao(appDatabase: AppDatabase): CommentDao {
        return appDatabase.commentDao()
    }
}