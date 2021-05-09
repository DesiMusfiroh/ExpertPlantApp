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
    val image: String? = null,
    val min_altitude: Int? = 0,
    val max_altitude: Int? = 0,
    val min_temperature: Int? = 0,
    val max_temperature: Int? = 0,
    val min_humidity: Int? = 0,
    val max_humidity: Int? = 0,
    val min_rainfall: Int? = 0,
    val max_rainfall: Int? = 0,
    val soils: List<Soil>?
) : Parcelable