package com.wellignton.androidtest.util.view

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    frameId: Int,
    addToBackStack: Boolean = false
) {
    supportFragmentManager.transact {
        replace(frameId, fragment, fragment.javaClass.simpleName)
        setPrimaryNavigationFragment(fragment)
        if (addToBackStack) {
            addToBackStack(fragment.javaClass.simpleName)
        }
    }
}

private inline fun androidx.fragment.app.FragmentManager.transact(action: androidx.fragment.app.FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitAllowingStateLoss()
}