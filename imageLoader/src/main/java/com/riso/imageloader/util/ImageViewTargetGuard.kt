package com.riso.imageloader.util

import android.widget.ImageView

class ImageViewTargetGuard {
    fun mark(target: ImageView, key: String) {
        target.setTag(key)
    }

    fun isStillValid(target: ImageView, key: String): Boolean {
        return target.tag == key
    }
}