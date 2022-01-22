package com.faire.weather.arch

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Class to make easy #RecyclerView.ViewHolder implementation
 * and support common functions
 */

abstract class BaseViewHolder(
    parent: ViewGroup,
    @LayoutRes layout: Int
) : RecyclerView.ViewHolder(
    LayoutInflater
        .from(parent.context)
        .inflate(layout, parent, false)
) {
    protected val context: Context = checkNotNull(parent.context)
}