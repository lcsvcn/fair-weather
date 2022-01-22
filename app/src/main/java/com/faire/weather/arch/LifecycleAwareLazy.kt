package com.faire.weather.arch

import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * Expose method to use for create lazy aware instance
 */
fun <T> lifecycleAwareLazy(lifecycle: Lifecycle, initializer: () -> T): Lazy<T> =
    LifecycleAwareLazyImpl(lifecycle, initializer)

/**
 * Class to control lazy variable with LifecycleOwner
 * to clear the value when receive the [Lifecycle.Event.ON_STOP] or [Lifecycle.Event.ON_DESTROY] event
 */
private class LifecycleAwareLazyImpl<out T>(
    lifecycle: Lifecycle,
    val initializer: () -> T
) : Lazy<T>, GenericLifecycleObserver {

    private object UNINITIALIZED

    private var manipulateValue: Any? = UNINITIALIZED

    init {
        lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_STOP,
            Lifecycle.Event.ON_DESTROY -> manipulateValue = UNINITIALIZED
            else -> return
        }
    }

    override val value: T
        get() {
            if (manipulateValue === UNINITIALIZED) {
                manipulateValue = initializer()
            }
            @Suppress("UNCHECKED_CAST")
            return manipulateValue as T
        }

    override fun isInitialized(): Boolean = manipulateValue !== UNINITIALIZED

    override fun toString(): String =
        if (isInitialized()) value.toString() else "LifecycleAwareLazy value not initialized yet."
}