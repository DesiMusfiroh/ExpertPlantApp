package com.desi.expertplantapp.ui.check

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.desi.expertplantapp.data.Plant
import com.desi.expertplantapp.data.Soil
import com.desi.expertplantapp.databinding.FragmentCheckFormBinding
import com.google.firebase.database.*

class CheckFormFragment : Fragment() {

    private lateinit var fragmentCheckFormBinding: FragmentCheckFormBinding
    private lateinit var database: DatabaseReference
    private lateinit var mapSoils: LinkedHashMap<String, String>
    private var arrayAdapter: ArrayAdapter<String>? = null
    val listSoils: MutableList<String> = ArrayList()
    private lateinit var listPlants: ArrayList<Plant>
    private lateinit var soilSelected: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCheckFormBinding = FragmentCheckFormBinding.inflate(layoutInflater, container, false)
        return fragmentCheckFormBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getListSoils()

        fragmentCheckFormBinding.apply {
            autoCompleteTextView.onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    soilSelected = parent?.getItemAtPosition(position).toString()
                }
            }
            btnSubmit.setOnClickListener{
                getDataInput()
            }
        }
    }

    private fun getListSoils() {
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
                arrayAdapter = context?.let { ArrayAdapter(it, R.layout.simple_spinner_dropdown_item, listSoils) }
                fragmentCheckFormBinding.autoCompleteTextView.setAdapter(arrayAdapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("firebase", "cancelled")
            }
        })
    }

    private fun getDataInput() {
        fragmentCheckFormBinding.apply {
            val altitude = etAltitude.text
            val temperature = etTemperature.text
            val humidity = etHumidity.text
            val rainfall = etRainfall.text
            val soil = soilSelected
            Log.d("firebase", "$altitude, $temperature, $humidity, $rainfall, $soil")

            val certainAltitudeId = certainAltitude.checkedRadioButtonId
            val certainTemperatureId = certainTemperature.checkedRadioButtonId
            val certainHumidityId = certainHumidity.checkedRadioButtonId
            val certainRainfallId = certainRainfall.checkedRadioButtonId
            val certainSoilId = certainSoil.checkedRadioButtonId
            val certainAltitude = resources.getResourceEntryName(certainAltitudeId)
            val certainTemperature = resources.getResourceEntryName(certainTemperatureId)
            val certainHumidity = resources.getResourceEntryName(certainHumidityId)
            val certainRainfall = resources.getResourceEntryName(certainRainfallId)
            val certainSoil = resources.getResourceEntryName(certainSoilId)

            Log.d("firebase", "$certainAltitude, $certainTemperature, $certainHumidity, $certainRainfall, $certainSoil")
        }
    }

    private fun getPlantsData() {
        database = FirebaseDatabase.getInstance().getReference("plants")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (plantSnapshot in snapshot.children) {
                        val plant = plantSnapshot.getValue(Plant::class.java)
                        listPlants.add(plant!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("firebase", "cancelled")
            }
        })
    }

}