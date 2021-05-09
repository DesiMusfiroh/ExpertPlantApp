package com.desi.expertplantapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*

class DataRepository {
    private lateinit var database: DatabaseReference

    fun getSoilsData() : LiveData<ArrayList<Soil>> {
        val soilResults = MutableLiveData<ArrayList<Soil>>()
        database = FirebaseDatabase.getInstance().getReference("soils")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val soilList = ArrayList<Soil>()
                if (snapshot.exists()){
                    for (soilSnapshot in snapshot.children) {
                        val soil = soilSnapshot.getValue(Soil::class.java)
                        val soilItem = Soil(soilSnapshot.key, soil?.name, soil?.desc, soil?.image)
                        soilList.add(soilItem)
                    }
                }
                soilResults.postValue(soilList)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("firebase", "gagal")
            }
        })
        return soilResults
    }

    fun getPlantsData(): LiveData<ArrayList<Plant>>  {
        val plantResults = MutableLiveData<ArrayList<Plant>>()
        database = FirebaseDatabase.getInstance().getReference("plants")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val plantList = ArrayList<Plant>()
                if (snapshot.exists()) {
                    for (plantSnapshot in snapshot.children) {
                        Log.d("firebase", "plant snap $plantSnapshot")
                        val plant = plantSnapshot.getValue(Plant::class.java)

                        Log.d("firebase", "plant  $plant")
                        val plantItem = Plant(
                            plantSnapshot.key,
                            plant?.name,
                            plant?.species,
                            plant?.desc,
                            plant?.benefit,
                            plant?.min_altitude,
                            plant?.max_altitude,
                            plant?.min_temperature,
                            plant?.max_temperature,
                            plant?.min_humidity,
                            plant?.max_humidity,
                            plant?.min_rainfall,
                            plant?.max_rainfall,
                            plant?.image,
                            0
                        )
                        plantList.add(plantItem)
                    }
                }
                Log.d("firebase", "plant list $plantList")
                plantResults.postValue(plantList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("firebase", "cancelled")
            }
        })
        return plantResults
    }
}

