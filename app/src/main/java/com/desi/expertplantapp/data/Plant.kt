package com.desi.expertplantapp.data

data class Plant (
        val name: String? = null,
        val species: String? = null,
        val desc: String? = null,
        val benefit: String? = null,
        val image: String? = null,
        val min_altitude: Int,
        val max_altitude: Int,
        val min_temperature: Int,
        val max_temperature: Int,
        val min_humidity: Int,
        val max_humidity: Int,
        val min_rainfall: Int,
        val max_rainfall: Int,
        val soils: List<Soil>
)