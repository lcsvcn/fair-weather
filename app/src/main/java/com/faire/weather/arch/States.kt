package com.faire.weather.arch


interface ViewState

sealed class LoadingState : ViewState {
    object Show : LoadingState()
    object Hide : LoadingState()
}

open class ErrorState(val title:String, val message: String) : ViewState

object EmptyState: ViewState