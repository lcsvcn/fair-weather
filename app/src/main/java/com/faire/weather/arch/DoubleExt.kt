package com.faire.weather.arch

import android.view.View
import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.formatCelsius() : String {
    return this.ceil().toString() + "°C"
}

fun Double.formatFahrenheit() : String {
    return this.ceil().toString() + "°F"
}

fun Double.ceil() : Double {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}

fun Double.floor() : Double {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toDouble()
}