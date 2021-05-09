package com.desi.expertplantapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plant (
    val key: String? = null,
    val name: String? = null,
    val species: String? = null,
    val desc: String? = null,
    val benefit: String? = null,
    val min_altitude: String? = null,
    val max_altitude: String? = null,
    val min_temperature: String? = null,
    val max_temperature: String? = null,
    val min_humidity: String? = null,
    val max_humidity: String? = null,
    val min_rainfall: String? = null,
    val max_rainfall: String? = null,
    val image: String? = null,
    var score: Int? = 0,
) : Parcelable