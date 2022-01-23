package com.faire.weather.arch

fun String.addLabelLow(): String {
    return "Low: $this"
}

fun String.addLabelHigh(): String {
    return "High: $this"
}

fun String.addLabelHumidity(): String {
    return "Humidity: $this"
}

