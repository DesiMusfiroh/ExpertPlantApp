package com.desi.expertplantapp.ui.plant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.desi.expertplantapp.R
import com.desi.expertplantapp.data.Plant
import com.desi.expertplantapp.databinding.ActivityPlantBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class PlantActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PLANT = "extra_plant"
    }
    private lateinit var plant: Plant
    private lateinit var binding: ActivityPlantBinding
    private lateinit var viewModel: PlantViewModel
    private var menu: Menu? = null
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PlantViewModel::class.java)

        plant = intent.getParcelableExtra<Plant>(EXTRA_PLANT) as Plant
        binding.apply {
            tvDesc.text = plant.desc
            tvBenefit.text = plant.benefit
            tvSpecies.text = plant.species
            tvAltitude.text = StringBuilder("${plant.min_altitude} - ${plant.max_altitude} mdpl")
            tvTemperature.text = StringBuilder("${plant.min_temperature} - ${plant.max_temperature} celcius")
            tvHumidity.text = StringBuilder("${plant.min_humidity} - ${plant.max_humidity} %")
            tvRainfall.text = StringBuilder("${plant.min_rainfall} - ${plant.max_rainfall} mm/year")
            tvScore.text = StringBuilder("Certainty Percentage : ${plant.score} %")
            Glide.with(this@PlantActivity)
                .load(plant.image)
                .into(imgImage)
        }

        supportActionBar!!.title = plant.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        this.menu = menu
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkFavorite(plant.key!!)
            Log.d("firebase", "count check favorite $count")
            withContext(Dispatchers.Main) {
                setFavoriteState(count)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        plant = intent.getParcelableExtra<Plant>(EXTRA_PLANT) as Plant
        if (item.itemId == R.id.action_favorite) {
            Log.d("firebase", "selected favorite $plant")
            if (statusFavorite) {
                viewModel.deleteFromFavorite(plant.key!!)
                Toast.makeText(baseContext, "Removed plant from favorite", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.insert(plant)
                Toast.makeText(baseContext, "Added plant to favorite", Toast.LENGTH_SHORT).show()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteState(count : Int?) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (count != null ) {
            if (count > 0) {
                menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_on)
                statusFavorite = true
            } else {
                menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_off)
                statusFavorite = false
            }
        }
    }
}