package com.wellignton.androidtest.util

import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar

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

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

fun View.showSnackBar(message: String, duration: Int, actionText: String, actionAction: () -> Unit) {
    Snackbar.make(this, message, duration)
        .setAction(actionText) { actionAction() }
        .show()
}