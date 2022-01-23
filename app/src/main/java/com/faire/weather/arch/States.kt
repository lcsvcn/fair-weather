package com.faire.weather.arch


interface ViewState

sealed class LoadingState : ViewState {
    object Show : LoadingState()
    object Hide : LoadingState()
}

sealed class  EmptyState: ViewState  {
    object Show : EmptyState()
    object Hide : EmptyState()
}

sealed class  ScrollState: ViewState  {
    object Top : ScrollState()
}


open class ErrorState(val title:String, val message: String) : ViewState

