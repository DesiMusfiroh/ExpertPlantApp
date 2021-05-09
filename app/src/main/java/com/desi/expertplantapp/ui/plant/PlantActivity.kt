package com.desi.expertplantapp.ui.plant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.desi.expertplantapp.R

class PlantActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PLANT = "extra_plant"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant)
    }
}