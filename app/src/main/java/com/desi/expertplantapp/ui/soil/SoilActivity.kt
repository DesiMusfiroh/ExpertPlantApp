package com.desi.expertplantapp.ui.soil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.desi.expertplantapp.data.Soil
import com.desi.expertplantapp.databinding.ActivitySoilBinding

class SoilActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SOIL = "extra_soil"
    }
    private lateinit var binding: ActivitySoilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val soil = intent.getParcelableExtra<Soil>(EXTRA_SOIL) as Soil

        binding.apply {
            tvDesc.text = soil.desc
            tvName.text = soil.name
            Glide.with(this@SoilActivity)
                    .load(soil.image)
                    .into(imgImage)
        }

        supportActionBar!!.title = soil.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

}