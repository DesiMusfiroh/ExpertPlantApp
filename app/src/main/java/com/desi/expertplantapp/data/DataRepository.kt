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
}

