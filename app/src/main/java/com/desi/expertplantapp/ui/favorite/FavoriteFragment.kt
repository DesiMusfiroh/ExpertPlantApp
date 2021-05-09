package com.desi.expertplantapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.desi.expertplantapp.data.FavoritePlant
import com.desi.expertplantapp.data.Plant
import com.desi.expertplantapp.databinding.FragmentFavoriteBinding
import com.desi.expertplantapp.ui.check.ResultAdapter
import com.desi.expertplantapp.ui.plant.PlantActivity

class FavoriteFragment : Fragment() {
    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private var plants = ArrayList<Plant>()
    private lateinit var plantAdapter: ResultAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plantAdapter = ResultAdapter(plants)
        plantAdapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        plantAdapter.setOnItemClickCallback(object : ResultAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Plant) {
                val detailIntent = Intent(context, PlantActivity::class.java)
                detailIntent.putExtra(PlantActivity.EXTRA_PLANT, data)
                startActivity(detailIntent)
            }
        })

        fragmentFavoriteBinding.apply {
            rvPlant.layoutManager = LinearLayoutManager(context)
            rvPlant.setHasFixedSize(true)
            rvPlant.adapter = plantAdapter
        }

        viewModel.getFavoritePlants()?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val list = mapList(it)
                plantAdapter.setList(list)
            }
        })
    }

    private fun mapList(plants: List<FavoritePlant>): ArrayList<Plant> {
        val listPlants = ArrayList<Plant>()
        for (plant in plants) {
            val plantMapped = Plant(
                plant.key,
                plant.name,
                plant.species,
                plant.desc,
                plant.benefit,
                plant.min_altitude,
                plant.max_altitude,
                plant.min_temperature,
                plant.max_temperature,
                plant.min_humidity,
                plant.max_humidity,
                plant.min_rainfall,
                plant.max_rainfall,
                plant.image,
                plant.score
            )
            listPlants.add(plantMapped)
        }
        return listPlants
    }
}