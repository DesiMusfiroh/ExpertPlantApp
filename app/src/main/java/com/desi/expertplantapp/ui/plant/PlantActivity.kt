package com.desi.expertplantapp.ui.plant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.desi.expertplantapp.data.Plant
import com.desi.expertplantapp.databinding.ActivityPlantBinding
import java.lang.StringBuilder

class PlantActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PLANT = "extra_plant"
    }
    private lateinit var binding: ActivityPlantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val plant = intent.getParcelableExtra<Plant>(EXTRA_PLANT) as Plant
        binding.apply {
            tvDesc.text = plant.desc
            tvBenefit.text = plant.benefit
            tvSpecies.text = plant.species
            tvAltitude.text = StringBuilder("${plant.min_altitude} - ${plant.max_altitude} mdpl")
            tvTemperature.text = StringBuilder("${plant.min_temperature} - ${plant.max_temperature} celcius")
            tvHumidity.text = StringBuilder("${plant.min_humidity} - ${plant.max_humidity} %")
            tvRainfall.text = StringBuilder("${plant.min_rainfall} - ${plant.max_rainfall} mm/year")
            tvScore.text = StringBuilder("Certainty Percentage : ${plant.score}")
            Glide.with(this@PlantActivity)
                .load(plant.image)
                .into(imgImage)
        }

        supportActionBar!!.title = plant.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}