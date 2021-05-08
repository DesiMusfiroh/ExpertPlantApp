package com.desi.expertplantapp.ui.soil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.desi.expertplantapp.databinding.ActivitySoilBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SoilActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SOIL = "extra_soil"
    }
    private lateinit var binding: ActivitySoilBinding
    private lateinit var database: DatabaseReference
    private lateinit var soilKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference
        val extras = intent.extras
        if (extras != null) {
            soilKey = extras.getString(EXTRA_SOIL).toString()
            getSoilData()
        }
    }

    private fun getSoilData() {
        database.child("users").child(soilKey).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value} ... soil ID = $soilKey")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }
}