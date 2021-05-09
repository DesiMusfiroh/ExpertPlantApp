package com.desi.expertplantapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.desi.expertplantapp.data.Soil
import com.desi.expertplantapp.databinding.FragmentHomeBinding
import com.desi.expertplantapp.ui.soil.SoilActivity

class HomeFragment : Fragment() {

    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var adapter: SoilAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentHomeBinding.rvSoil.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        fragmentHomeBinding.rvSoil.setHasFixedSize(true)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]
        viewModel.getSoils().observe(viewLifecycleOwner, { soils ->
            fragmentHomeBinding.rvSoil.adapter = SoilAdapter(soils)
            adapter = fragmentHomeBinding.rvSoil.adapter as SoilAdapter
            adapter.setOnItemClickCallback(object : SoilAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Soil) {
                    Intent(activity, SoilActivity::class.java).also {
                        it.putExtra(SoilActivity.EXTRA_SOIL, data.key)
                        startActivity(it)
                    }
                }
            })
        })

    }
}