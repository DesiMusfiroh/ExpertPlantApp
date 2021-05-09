package com.desi.expertplantapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.desi.expertplantapp.data.FavoritePlant
import com.desi.expertplantapp.data.PlantDao
import com.desi.expertplantapp.data.PlantDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application){
    val plant = MutableLiveData<FavoritePlant>()

    private var plantDatabase: PlantDatabase? = PlantDatabase.getDatabase(application)
    private var plantDao: PlantDao?
    init { plantDao = plantDatabase?.plantDao() }

    fun getFavoritePlants(): LiveData<List<FavoritePlant>>? {
        return plantDao?.getFavoritePlants()
    }
}