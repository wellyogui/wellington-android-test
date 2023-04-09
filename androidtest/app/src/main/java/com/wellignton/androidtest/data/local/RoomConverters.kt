package com.wellignton.androidtest.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wellignton.androidtest.data.model.PostEntity

class RoomConverters {
    @TypeConverter
    fun fromString(value: String): List<PostEntity> {
        val listType = object : TypeToken<List<PostEntity>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<PostEntity>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromPostString(value: String): PostEntity {
        val listType = object : TypeToken<PostEntity?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: PostEntity?): String {
        return Gson().toJson(list)
    }
}