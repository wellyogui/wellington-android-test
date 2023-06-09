package com.wellignton.androidtest

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by well_ on 08/04/2023 for Android test.
 */
@HiltAndroidApp
class AppContext : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}