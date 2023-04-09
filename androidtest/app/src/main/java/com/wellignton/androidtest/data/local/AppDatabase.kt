package com.wellignton.androidtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wellignton.androidtest.data.model.PostEntity

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}