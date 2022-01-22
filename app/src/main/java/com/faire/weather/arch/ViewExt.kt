package com.faire.weather.arch

import android.view.View

fun View.fadeOutGone(duration: Long = 300, startDelay: Long = 0, endAction: (() -> Unit)? = null) {
    if (this.isGone()) return
    alpha = 1f
    animate()
        .alpha(0f)
        .setDuration(duration)
        .setStartDelay(startDelay)
        .withEndAction {
            gone()
            endAction?.invoke()
        }
        .start()
}

fun View.fadeIn(duration: Long = 300, startDelay: Long = 0, endAction: (() -> Unit)? = null) {
    if (this.isVisible()) return
    visible()
    alpha = 0f
    animate()
        .alpha(1f)
        .setDuration(duration)
        .setStartDelay(startDelay)
        .withEndAction { endAction?.invoke() }
        .start()
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE