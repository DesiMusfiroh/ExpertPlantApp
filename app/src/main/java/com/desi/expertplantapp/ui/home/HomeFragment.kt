package com.desi.expertplantapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.desi.expertplantapp.data.Soil
import com.desi.expertplantapp.databinding.FragmentHomeBinding
import com.desi.expertplantapp.ui.soil.SoilActivity
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: SoilAdapter
    private lateinit var listSoils: ArrayList<Soil>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentHomeBinding.rvSoil.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        fragmentHomeBinding.rvSoil.setHasFixedSize(true)
        listSoils = arrayListOf()
        getSoilsData()
    }

    private fun getSoilsData() {
        database = FirebaseDatabase.getInstance().getReference("soils")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (soilSnapshot in snapshot.children) {
                        val soil = soilSnapshot.getValue(Soil::class.java)
                        listSoils.add(soil!!)
                    }
                    fragmentHomeBinding.rvSoil.adapter = SoilAdapter(listSoils)
                    adapter = fragmentHomeBinding.rvSoil.adapter as SoilAdapter
                    adapter.setOnItemClickCallback(object : SoilAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: Soil) {
                            Intent(activity, SoilActivity::class.java).also {
                                it.putExtra(SoilActivity.EXTRA_SOIL, data.key)
                                startActivity(it)
                            }
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("firebase", "gagal")
            }
        })

    }

}