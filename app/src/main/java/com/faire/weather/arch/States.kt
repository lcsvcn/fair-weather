package com.faire.weather.arch

import android.app.Activity

interface ViewState

sealed class LoadingState(val loadingMessage: String? = null) : ViewState {
    object Show : LoadingState()
    object Hide : LoadingState()
    data class ShowWithMessage(val message: String) : LoadingState(message)
    data class HideWithMessage(val message: String) : LoadingState(message)
}

open class ErrorState(val title:String, val message: String) : ViewState

object EmptyState: ViewState