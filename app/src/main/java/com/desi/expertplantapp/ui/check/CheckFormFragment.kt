package com.desi.expertplantapp.ui.check

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.desi.expertplantapp.data.Check
import com.desi.expertplantapp.databinding.FragmentCheckFormBinding

class CheckFormFragment : Fragment() {

    private lateinit var fragmentCheckFormBinding: FragmentCheckFormBinding
    private var arrayAdapter: ArrayAdapter<String>? = null
    private lateinit var soilSelected: String
    private lateinit var viewModel: CheckFormViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCheckFormBinding = FragmentCheckFormBinding.inflate(layoutInflater, container, false)
        return fragmentCheckFormBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[CheckFormViewModel::class.java]

        viewModel.getListSoilName().observe(viewLifecycleOwner, { listSoilName ->
            arrayAdapter = context?.let { ArrayAdapter(it, R.layout.simple_spinner_dropdown_item, listSoilName) }
            fragmentCheckFormBinding.autoCompleteTextView.setAdapter(arrayAdapter)
        })

        fragmentCheckFormBinding.apply {
            autoCompleteTextView.onItemClickListener = object : AdapterView.OnItemClickListener {
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    soilSelected = parent?.getItemAtPosition(position).toString()
                }
            }
            btnSubmit.setOnClickListener{
                checkingProcess()
            }
        }
    }

    private fun checkingProcess() {
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

            val check = Check(altitude, temperature, humidity, rainfall, soil, certainAltitude, certainTemperature, certainHumidity, certainRainfall, certainSoil )
            viewModel.getPlantsData().observe(viewLifecycleOwner, { plantsData ->
                val certainAltitudeExpert = 100
                val certainTemperatureExpert = 75
                val certainHumidityExpert = 50
                val certainRainfallExpert = 25
                val totalCertainExpert = certainAltitudeExpert + certainTemperatureExpert + certainHumidityExpert + certainRainfallExpert

                for (plant in plantsData) {
                    if (check.altitude.toString().toInt() >= plant.min_altitude.toString().toInt() &&  check.altitude.toString().toInt() <= plant.max_altitude.toString().toInt()) {
                        var certainAltitudeUser = 0
                        when (certainAltitude) {
                            "certain_altitude_one" -> certainAltitudeUser = 0
                            "certain_altitude_two" -> certainAltitudeUser = 25
                            "certain_altitude_three" -> certainAltitudeUser = 50
                            "certain_altitude_four" -> certainAltitudeUser = 75
                            "certain_altitude_five" -> certainAltitudeUser = 100
                        }
                        plant.score = plant.score?.plus((certainAltitudeExpert * certainAltitudeUser))
                    }
                    if (check.temperature.toString().toInt() >= plant.min_temperature.toString().toInt() &&  check.temperature.toString().toInt() <= plant.max_temperature.toString().toInt()) {
                        var certainTemperatureUser = 0
                        when (certainTemperature) {
                            "certain_temperature_one" -> certainTemperatureUser = 0
                            "certain_temperature_two" -> certainTemperatureUser = 25
                            "certain_temperature_three" -> certainTemperatureUser = 50
                            "certain_temperature_four" -> certainTemperatureUser = 75
                            "certain_temperature_five" -> certainTemperatureUser = 100
                        }
                        plant.score = plant.score?.plus((certainTemperatureExpert * certainTemperatureUser))
                    }
                    if (check.humidity.toString().toInt() >= plant.min_humidity.toString().toInt() &&  check.humidity.toString().toInt() <= plant.max_humidity.toString().toInt()) {
                        var certainHumidityUser = 0
                        when (certainHumidity) {
                            "certain_humidity_one" -> certainHumidityUser = 0
                            "certain_humidity_two" -> certainHumidityUser = 25
                            "certain_humidity_three" -> certainHumidityUser = 50
                            "certain_humidity_four" -> certainHumidityUser = 75
                            "certain_humidity_five" -> certainHumidityUser = 100
                        }
                        plant.score = plant.score?.plus((certainHumidityExpert * certainHumidityUser))
                    }
                    if (check.rainfall.toString().toInt() >= plant.min_rainfall.toString().toInt() &&  check.rainfall.toString().toInt() <= plant.max_rainfall.toString().toInt()) {
                        var certainRainfallUser = 0
                        when (certainRainfall) {
                            "certain_rainfall_one" -> certainRainfallUser = 0
                            "certain_rainfall_two" -> certainRainfallUser = 25
                            "certain_rainfall_three" -> certainRainfallUser = 50
                            "certain_rainfall_four" -> certainRainfallUser = 75
                            "certain_rainfall_five" -> certainRainfallUser = 100
                        }
                        plant.score = plant.score?.plus((certainRainfallExpert * certainRainfallUser))
                    }
                    plant.score = plant.score?.div(totalCertainExpert + 50)
                    Log.d("firebase", "${plant.score}")
                }
                Intent(activity, CheckResultActivity::class.java).also {
                    it.putExtra(CheckResultActivity.EXTRA_PLANTS_DATA, plantsData)
                    startActivity(it)
                }
            })
        }

    }
}
