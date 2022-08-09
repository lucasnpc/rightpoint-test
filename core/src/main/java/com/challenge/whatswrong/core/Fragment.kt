package com.challenge.whatswrong.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * Launches and runs the given block when the [Lifecycle] of the Fragment's View
 * [LifecycleCoroutineScope] is at least in [Lifecycle.State.CREATED] state.
 *
 * The returned [Job] will be cancelled when the [Lifecycle] of the View is destroyed.
 * @see Lifecycle.whenCreated
 * @see LifecycleCoroutineScope
 */
fun Fragment.launchWhenViewCreated(block: suspend CoroutineScope.() -> Unit) {
    viewLifecycleOwnerLiveData.observe(this) { owner ->
        owner.lifecycleScope.launchWhenCreated(block)
    }
}
