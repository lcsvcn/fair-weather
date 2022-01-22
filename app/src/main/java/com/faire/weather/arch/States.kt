package com.faire.weather.arch

import android.app.Activity

interface ViewState

sealed class LoadingState(val loadingMessage: String? = null) : ViewState {
    object Show : LoadingState()
    object Hide : LoadingState()
    data class ShowWithMessage(val message: String) : LoadingState(message)
    data class HideWithMessage(val message: String) : LoadingState(message)
}

open class ErrorState(val message: String) : ViewState

open class FinishState(val activityResult: Int = Activity.RESULT_OK, val mapExtras: Map<String, Any>? = null) : ViewState

object EmptyState: ViewState