package com.wellignton.androidtest.util

import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup

/**
 * Created by well_ on 08/04/2023 for Android test.
 */

fun View.visible(animate: Boolean = true) {
    if (this.visibility != View.VISIBLE) {
        if (parent is ViewGroup && animate) {
            TransitionManager.beginDelayedTransition(parent as ViewGroup)
        }
        visibility = View.VISIBLE
    }
}

fun View.gone(animate: Boolean = true) {
    if (this.visibility != View.GONE) {
        if (parent is ViewGroup && animate) {
            TransitionManager.beginDelayedTransition(parent as ViewGroup)
        }
        visibility = View.GONE
    }
}