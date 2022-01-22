package com.faire.weather.arch

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KProperty

inline fun <reified T : Fragment> AppCompatActivity.bindFragment(@IdRes id: Int) = lazy {
    supportFragmentManager.findFragmentById(id) as T
}

fun <T : View> AppCompatActivity.bind(@IdRes id: Int) = lazy { findViewById<T>(id) }

fun <T : View> Fragment.bind(@IdRes id: Int) = lifecycleAwareLazy(lifecycle) {
    view?.findViewById<T>(id) ?: if (isAdded) {
        throw IllegalStateException("Could not find view by id ${resources.getResourceEntryName(id)}")
    } else {
        throw IllegalStateException("Could not find a view in ${this::class.java.name}")
    }
}

operator fun <T> Lazy<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {}

inline fun <reified T : View> AppCompatActivity.bind(vararg idList: Int) = lazy {
    val arrayView = mutableListOf<T>()
    for (id in idList) {
        arrayView.add(findViewById(id))
    }
    arrayView.toTypedArray()
}

inline fun <reified T : View> View.bind(vararg idList: Int) = lazy {
    val arrayView = mutableListOf<T>()
    for (id in idList) {
        arrayView.add(findViewById(id))
    }
    arrayView.toTypedArray()
}

inline fun <reified T : View> Fragment.bind(vararg idList: Int) = lazy {
    val arrayView = mutableListOf<T>()
    for (id in idList) {
        arrayView.add(checkNotNull(view?.findViewById(id)))
    }
    arrayView.toTypedArray()
}

fun <T : View> RecyclerView.ViewHolder.bind(@IdRes id: Int) = lazy { itemView.findViewById<T>(id) }

fun <T : View> View.bind(@IdRes id: Int) = lazy { findViewById<T>(id) }