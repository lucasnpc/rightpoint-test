package com.challenge.whatswrong.core

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty

/**
 * An [AutoClearedValue] that is specifically a [ViewBinding] inside of a Fragment.
 */
class FragmentViewBindingDelegate<V : ViewBinding>(
    fragment: Fragment,
    private val bindingFactory: (View) -> V
) : AutoClearedValue<V>(fragment) {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): V {
        val value = _value

        if (value != null) {
            return value
        }

        with(thisRef.viewLifecycleOwner.lifecycle.currentState) {
            check(isAtLeast(Lifecycle.State.INITIALIZED))
        }

        return bindingFactory(thisRef.requireView()).also { _value = it }
    }
}

/**
 * A convenience extension function to declare a [FragmentViewBindingDelegate] inside of a Fragment.
 */
fun <B : ViewBinding> Fragment.viewBinding(factory: (View) -> B): FragmentViewBindingDelegate<B> {
    return FragmentViewBindingDelegate(this, factory)
}
