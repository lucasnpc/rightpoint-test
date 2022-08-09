package com.challenge.whatswrong.core

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

/**
 * A convenience extension function to declare a [Lazy] [ViewBinding] inside of an [Activity].
 * The binding will not be inflated until it is invoked within the Activity, usually on the
 * [Activity.setContentView] call.
 */
inline fun <V : ViewBinding> Activity.viewBinding(
    crossinline inflateBinding: (LayoutInflater) -> V
) = lazy(LazyThreadSafetyMode.NONE) {
    inflateBinding(layoutInflater)
}
