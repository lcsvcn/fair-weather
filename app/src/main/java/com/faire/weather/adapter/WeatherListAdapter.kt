package com.faire.weather.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faire.weather.R
import com.faire.weather.arch.BaseViewHolder
import com.faire.weather.arch.bind
import com.faire.weather.api.data.Weather
import com.faire.weather.adapter.WeatherListAdapter.WeatherViewHolder
import com.faire.weather.arch.addLabelHigh
import com.faire.weather.arch.addLabelLow
import com.faire.weather.arch.formatCelsius
import java.util.TreeMap


class WeatherListAdapter : RecyclerView.Adapter<WeatherViewHolder>() {

    private val weatherList = mutableListOf<Weather>()

    private val images by createMapImages()

    fun setList(list: List<Weather>) {
        weatherList.clear()
        weatherList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WeatherViewHolder(parent)

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount() = weatherList.size

    inner class WeatherViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder(parent, R.layout.item_weather_card) {

        private val txtCurrentTemperature: TextView by bind(R.id.txt_current_temperature)
        private val txtWeatherState: TextView by bind(R.id.txt_weather_state)
        private val txtHighTemperature: TextView by bind(R.id.txt_high_temperature)
        private val txtLowTemperature: TextView by bind(R.id.txt_low_temperature)
        private val txtDate: TextView by bind(R.id.txt_date)
        private val imgWeatherIcon: ImageView by bind(R.id.img_weather_icon)

        fun bind(weather: Weather) {
            weather.currentTemperature?.let {
                txtCurrentTemperature.text = it.formatCelsius()
            }
            weather.highTemperature?.let {
                txtHighTemperature.text = it.formatCelsius().addLabelHigh()
            }
            weather.lowTemperature?.let {
                txtLowTemperature.text = it.formatCelsius().addLabelLow()
            }
            weather.stateName?.let {
                txtWeatherState.text = it
            }
            weather.date?.let{
                txtDate.text = it
            }
            weather.stateAbbreviated?.let {
                imgWeatherIcon.setBackgroundResource(getDrawableIdFromName(it))
            }
        }
    }

    private fun getDrawableIdFromName(name: String): Int {
        return images[name] ?: R.drawable.ic_launcher_background
    }

    private fun createMapImages() = lazy {
        TreeMap<String, Int>().apply {
            put("sn", R.drawable.ic_sn)
            put("sl", R.drawable.ic_sl)
            put("h", R.drawable.ic_h)
            put("t", R.drawable.ic_t)
            put("hr", R.drawable.ic_hr)
            put("lr", R.drawable.ic_lr)
            put("s", R.drawable.ic_s)
            put("hc", R.drawable.ic_hc)
            put("lc", R.drawable.ic_lc)
            put("c", R.drawable.ic_c)
        }
    }
}
