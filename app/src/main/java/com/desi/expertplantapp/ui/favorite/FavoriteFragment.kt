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
import com.desi.expertplantapp.databinding.FragmentFavoriteBinding
import com.desi.expertplantapp.ui.plant.PlantActivity

class FavoriteFragment : Fragment() {
    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding
    private lateinit var adapter: FavoritePlantAdapter
    private var listPlants = ArrayList<FavoritePlant>()
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        fragmentFavoriteBinding.rvPlant.layoutManager = LinearLayoutManager(context)
        adapter = FavoritePlantAdapter(listPlants)
        fragmentFavoriteBinding.rvPlant.adapter = adapter
        adapter.setOnItemClickCallback(object : FavoritePlantAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoritePlant) {
//                val detailIntent = Intent(context, PlantActivity::class.java)
//                detailIntent.putExtra(PlantActivity.EXTRA_PLANT, data)
//                startActivity(detailIntent)
            }
        })

        viewModel.getFavoritePlants()?.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.setList(it)
            }
        })
    }

//    private fun mapList(plants: List<FavoritePlant>): ArrayList<Plant> {
//        val listPlants = ArrayList<Plant>()
//        for (plant in plants) {
//            val plantMapped = Plant(
//                plant.key,
//                plant.name,
//                plant.species,
//                plant.desc,
//                plant.benefit,
//                plant.min_altitude,
//                plant.max_altitude,
//                plant.min_temperature,
//                plant.max_temperature,
//                plant.min_humidity,
//                plant.max_humidity,
//                plant.min_rainfall,
//                plant.max_rainfall,
//                plant.image,
//                plant.score
//            )
//            listPlants.add(plantMapped)
//        }
//        return listPlants
//    }
}