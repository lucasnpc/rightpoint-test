package com.challenge.whatswrong.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A lazy property that gets cleaned up when the Fragment's View is destroyed.
 *
 * Accessing this variable while the Fragment's View is destroyed will throw IllegalStateException.
 */
open class AutoClearedValue<T : Any>(fragment: Fragment) : ReadWriteProperty<Fragment, T> {
    protected var _value: T? = null

    init {
        // Sets the backing value to null upon destroying the View
        val onDestroy = object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                _value = null
            }
        }

        val onCreate = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(
                    fragment,
                    { viewLifecycleOwner ->
                        viewLifecycleOwner.lifecycle.addObserver(onDestroy)
                    }
                )
            }
        }

        fragment.lifecycle.addObserver(onCreate)
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value ?: throw IllegalStateException("Cannot access auto-cleared value.")
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)
