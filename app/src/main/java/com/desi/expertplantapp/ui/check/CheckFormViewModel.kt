package com.desi.expertplantapp.ui.check

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.desi.expertplantapp.data.Soil
import com.google.firebase.database.*

class CheckFormViewModel : ViewModel() {
    private lateinit var database: DatabaseReference
    private lateinit var mapSoils: LinkedHashMap<String, String>
    val listSoils: MutableList<String> = ArrayList()

    fun getListSoilName() : LiveData<List<String>> {
        val listResults = MutableLiveData<List<String>>()
        database = FirebaseDatabase.getInstance().getReference("soils")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (soilSnapshot in snapshot.children) {
                        val soil = soilSnapshot.getValue(Soil::class.java)
                        mapSoils = LinkedHashMap()
                        mapSoils.put(soilSnapshot.key!!, soil?.name!!)
                        listSoils.add(soil.name)
                    }
                    Log.d("firebase", "map soils = $mapSoils")
                }
                listResults.postValue(listSoils)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("firebase", "cancelled")
            }
        })
        return listResults
    }
}