package com.desi.expertplantapp.data

import android.text.Editable

data class Check(
    val altitude: Editable?,
    val temperature: Editable?,
    val humidity: Editable?,
    val rainfall: Editable?,
    val soil: String? = null,
    val certain_altitude: String,
    val certain_temperature: String,
    val certain_humidity: String,
    val certain_rainfall: String,
    val certain_soil: String,
)
