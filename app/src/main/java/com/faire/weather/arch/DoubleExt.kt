package com.faire.weather.arch

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.formatCelsius() : String {
    return this.ceil().toString() + "°C"
}

fun Double.ceil() : Double {
    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this).toDouble()
}
