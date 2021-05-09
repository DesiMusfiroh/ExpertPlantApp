package com.desi.expertplantapp.ui.check

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.desi.expertplantapp.data.Plant
import com.desi.expertplantapp.databinding.ActivityCheckResultBinding
import com.desi.expertplantapp.ui.plant.PlantActivity

class CheckResultActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PLANTS_DATA = "extra_soil"
    }
    private lateinit var binding: ActivityCheckResultBinding
    private lateinit var adapter: ResultAdapter
    private lateinit var listPlants: ArrayList<Plant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        listPlants = intent.getParcelableArrayListExtra<Plant>(EXTRA_PLANTS_DATA) as ArrayList<Plant>

        binding.rvPlant.layoutManager = LinearLayoutManager(this)
        adapter = ResultAdapter(listPlants)
        binding.rvPlant.adapter = adapter
        adapter.setOnItemClickCallback(object : ResultAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Plant) {
                val detailIntent = Intent(this@CheckResultActivity, PlantActivity::class.java)
                detailIntent.putExtra(PlantActivity.EXTRA_PLANT, data)
                startActivity(detailIntent)
            }
        })
    }
}