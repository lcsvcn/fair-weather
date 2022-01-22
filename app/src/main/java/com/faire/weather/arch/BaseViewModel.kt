package com.faire.weather.arch

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    /**
     * Used to choose from UI or IO thread.
     */
    var poolProvider: PoolProvider = PoolProvider()

    /**
     * Job used to store the execution, so we can cancel it when the view is detached.
     */
    private val job = Job()

    /**
     * The single LiveData that the view will observe.
     */
    val viewState = MediatorLiveData<ViewState>()

    /**
     * A list containing all posted VieStates, so if the view is recreated all events can be recovered an posted again.
     */
    private val viewStateList: MutableList<MutableLiveData<*>> = mutableListOf()

    /**
     * The default live data to notify loading state.
     */
    private val loadingLiveData = intoMediator<LoadingState>()

    /**
     * The default live data to notify error state.
     */
    protected val errorLiveData = intoMediator<ErrorState>()

    /**
     * The default live data to notify finish state.
     */
    protected val finishLiveData = intoMediator<FinishState>()


    /**
     * Observe the onCreate events to (re)post all stored states.
     * If the activity was recreated the last state will be restored.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        viewState.value = null
        viewStateList.forEach {
            if (it.value != null && it.value != viewState.value) {
                viewState.postValue(it.value as? ViewState)
            }
        }
    }

    /**
     * Create a LiveData with the given generic type and make the LiveDataMediator observes it.
     * Then the view can observe just the mediator and receive the posts from every LiveData object.
     */
    protected fun <T : ViewState> intoMediator(shouldAddInViewStateList: Boolean = true): MutableLiveData<T> {
        val liveData = MutableLiveData<T>()
        viewState.addSource(liveData) {
            Timber.d("ViewState -> ${it.javaClass.simpleName}")
            viewState.value = it
        }
        if (shouldAddInViewStateList) {
            viewStateList.add(liveData)
        }
        return liveData
    }

    /**
     * Execute some async function without handling the loading state
     */
    protected fun launch(asyncBlock: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(poolProvider.ui + job).launch {
            asyncBlock()
        }
    }

    /**
     * Post LoadingState.SHOW, execute some async function and post LoadingState.HIDE.
     */
    protected fun load(asyncBlock: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(poolProvider.ui + job).launch {
            showLoading()
            asyncBlock()
            hideLoading()
        }
    }

    fun hideLoading() {
        loadingLiveData.postValue(LoadingState.Hide)
    }

    fun hideLoadingWithMessage(message: String) {
        loadingLiveData.postValue(LoadingState.HideWithMessage(message))
    }

    fun showLoading() {
        loadingLiveData.postValue(LoadingState.Show)
    }

    fun showLoadingWithMessage(message: String) {
        loadingLiveData.postValue(LoadingState.ShowWithMessage(message))
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    override fun onCleared() {
        viewStateList.clear()
        job.cancel()
    }
}