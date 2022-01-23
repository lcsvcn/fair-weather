package com.faire.weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.faire.weather.R.layout
import com.faire.weather.adapter.WeatherListAdapter
import com.faire.weather.arch.LoadingState
import com.faire.weather.arch.gone
import com.faire.weather.arch.setValue
import com.faire.weather.arch.visible
import com.faire.weather.databinding.ActivityWeatherListBinding
import com.faire.weather.viewmodel.WeatherListViewModel
import androidx.activity.viewModels
import com.faire.weather.R
import androidx.appcompat.widget.SearchView
import com.faire.weather.arch.EmptyState
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class WeatherListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWeatherListBinding.inflate(layoutInflater) }

    private var adapter by lazy { WeatherListAdapter() }

    private val viewModel: WeatherListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Remove from viewModel
        viewModel.setupApi()

        setupViews()

        handleLoadingState()
        handleErrorState()
        handleEmptyListState()
        handleListListState()
        handleScrollState()
    }

    private fun setupViews() {
        with(binding) {
            rcvWeatherList.adapter = this@WeatherListActivity.adapter

            btnScrollToTop.setOnClickListener { viewModel.onFabScrollTopTopClick() }
            ctnAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
                val scrollRange = barLayout?.totalScrollRange ?: 0
                if (scrollRange + verticalOffset == 0) {
                    btnScrollToTop.visible()
                } else {
                    btnScrollToTop.gone()
                }
            })
            ctnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.showLoading()
                        viewModel.onQueryTextSubmit(query)
                        viewModel.hideLoading()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    Timber.i("onQueryTextChange")
                    if(newText.isBlank()) {
                        viewModel.showEmptyState()
                    }
                    return false
                }
            })
        }
    }

    private fun handleScrollState() {
        viewModel.scrollLiveData.observe(this, {
            with(binding) {
                ctnAppBar.setExpanded(true)
                rcvWeatherList.apply {
                    stopScroll()
                    layoutManager?.scrollToPosition(0)
                }
            }
        })
    }

    private fun handleLoadingState() {
        viewModel.loadingLiveData.observe(this, {
            with(binding) {
                if (it is LoadingState.Show) {
                    pgrLoadingIndicator.visible()
                } else {
                    pgrLoadingIndicator.gone()
                }
            }
        })
    }

    private fun handleErrorState() {
        viewModel.errorLiveData.observe(this, {
            with(binding) {
                if (it.title.isNotBlank() && it.message.isNotBlank()) {
                    ctnAlertMessage.visible()
                    rcvWeatherList.gone()
                    txtTitle.text = it.title
                    txtMessage.text = it.message
                } else {
                    ctnAlertMessage.gone()
                }
            }
        })
    }

    private fun handleEmptyListState() {
        viewModel.emptyLiveData.observe(this, {
            with(binding) {
                if (it is EmptyState.Show) {
                    ctnAlertMessage.visible()
                    rcvWeatherList.gone()
                    txtTitle.text = getString(R.string.empty_title);
                    txtMessage.text = getString(R.string.empty_message);
                } else {
                    ctnAlertMessage.gone()
                }
            }
        })
    }

    private fun handleListListState() {
        viewModel.listLiveData.observe(this, {
            adapter.setList(it)
            binding.rcvWeatherList.visible()
        })
    }
}