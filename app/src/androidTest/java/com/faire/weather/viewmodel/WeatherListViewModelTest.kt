package com.faire.weather.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faire.weather.arch.EmptyState
import com.faire.weather.arch.LoadingState
import com.faire.weather.arch.ScrollState
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class WeatherListViewModelTest : TestCase() {

    private val viewModel = WeatherListViewModel()

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Test
    fun showEmptyState() {
        // Configuration
        val mockLiveData = viewModel.emptyLiveData

        // Execution
        viewModel.showEmptyState()

        // Assertion
        assertNotNull(mockLiveData)
        assertNotNull(mockLiveData.value)
        assertEquals(mockLiveData.value, EmptyState.Show)
    }

    @Test
    fun hideEmptyState() {
        // Configuration
        val mockLiveData = viewModel.emptyLiveData

        // Execution
        viewModel.showEmptyState()

        // Assertion
        assertNotNull(mockLiveData)
        assertNotNull(mockLiveData.value)
        assertEquals(mockLiveData.value, EmptyState.Show)
    }

    @Test
    fun hideLoading() {
        // Configuration
        val mockLiveData = viewModel.loadingLiveData

        // Execution
        viewModel.hideLoading()

        // Assertion
        assertNotNull(mockLiveData)
        assertNotNull(mockLiveData.value)
        assertEquals(mockLiveData.value, LoadingState.Hide)
    }

    @Test
    fun showLoading() {
        // Configuration
        val mockLiveData = viewModel.loadingLiveData

        // Execution
        viewModel.showLoading()

        // Assertion
        assertNotNull(mockLiveData)
        assertNotNull(mockLiveData.value)
        assertEquals(mockLiveData.value, LoadingState.Show)
    }


    @Test
    fun onFabScrollTopTopClick() {
        // Configuration
        val mockLiveData = viewModel.scrollLiveData

        // Execution
        viewModel.onFabScrollTopTopClick()

        // Assertion
        assertNotNull(mockLiveData)
        assertNotNull(mockLiveData.value)
        assertEquals(mockLiveData.value, ScrollState.Top)
    }

}