package com.desi.expertplantapp.ui.plant

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.desi.expertplantapp.data.FavoritePlant
import com.desi.expertplantapp.data.Plant
import com.desi.expertplantapp.data.PlantDao
import com.desi.expertplantapp.data.PlantDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PlantViewModel(application: Application) : AndroidViewModel(application) {

    private var plantDao: PlantDao?
    private var plantDb : PlantDatabase?

    init {
        plantDb = PlantDatabase.getDatabase(application)
        plantDao = plantDb?.plantDao()
    }

    fun insert(plant: Plant) {
        Log.d("firebase", "insert $plant")
        insertToDb(plant)
    }

    private fun insertToDb(plant: Plant) {
        Log.d("firebase", "insert to db start $plant")
        val id = UUID.randomUUID().toString()
        CoroutineScope(Dispatchers.IO).launch {
            val favoritePlant = FavoritePlant(
                id,
                plant.key,
                plant.name,
                plant.species,
                plant.desc,
                plant.benefit,
                plant.min_altitude,
                plant.max_altitude,
                plant.min_temperature,
                plant.max_temperature,
                plant.min_humidity,
                plant.max_humidity,
                plant.min_rainfall,
                plant.max_rainfall,
                plant.image,
                plant.score
            )
            plantDao?.insert(favoritePlant)
            Log.d("firebase", "insert to db end$favoritePlant")
        }
    }

    suspend fun checkFavorite(key: String) : Int? {
        return plantDao?.checkFavorite(key)
    }

    fun deleteFromFavorite(key: String) {
        CoroutineScope(Dispatchers.IO).launch {
            plantDao?.deleteFromFavorite(key)
        }
    }
}