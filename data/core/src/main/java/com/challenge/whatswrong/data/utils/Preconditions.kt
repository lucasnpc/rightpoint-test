@file:Suppress("NOTHING_TO_INLINE")
package com.challenge.whatswrong.data.utils

import android.os.Looper

inline fun checkMainThread() {
    check(Looper.myLooper() == Looper.getMainLooper()) {
        "Expected to be called on the main thread but was ${Thread.currentThread().name}"
    }
}

inline fun checkBackgroundThread() {
    check(Looper.myLooper() != Looper.getMainLooper()) {
        "Expected to be called on background thread but was the main thread"
    }
}
