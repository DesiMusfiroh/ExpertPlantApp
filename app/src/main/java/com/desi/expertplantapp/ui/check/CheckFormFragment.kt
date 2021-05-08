package com.desi.expertplantapp.ui.check

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.desi.expertplantapp.data.Soil
import com.desi.expertplantapp.databinding.FragmentCheckFormBinding
import com.google.firebase.database.*

class CheckFormFragment : Fragment() {

    private lateinit var fragmentCheckFormBinding: FragmentCheckFormBinding
    private lateinit var database: DatabaseReference
    private lateinit var mapSoils: LinkedHashMap<String, String>
    private var arrayAdapter: ArrayAdapter<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCheckFormBinding = FragmentCheckFormBinding.inflate(layoutInflater, container, false)
        return fragmentCheckFormBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapSoils = LinkedHashMap()
        getListSoils()

    }

    private fun getListSoils() {
        val listSoils: MutableList<String> = ArrayList()
        database = FirebaseDatabase.getInstance().getReference("soils")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (soilSnapshot in snapshot.children) {
                        val soil = soilSnapshot.getValue(Soil::class.java)
                        mapSoils.put(soilSnapshot.key!!, soil?.name!!)
                        listSoils.add(soil.name)
                    }
                    Log.d("firebase", "map soils = $mapSoils")
                }
                arrayAdapter = context?.let { ArrayAdapter(it, R.layout.simple_spinner_dropdown_item, listSoils) }
                fragmentCheckFormBinding.autoCompleteTextView.setAdapter(arrayAdapter)
                //hashMapAdapter = LinkedHashMapAdapter<String, String>(this, R.layout.simple_spinner_dropdown_item, mapSoils)
                //fragmentCheckFormBinding.autoCompleteTextView.setAdapter(hashMapAdapter)
                }

            override fun onCancelled(error: DatabaseError) {
                Log.d("firebase", "cancelled")
            }
        })
    }
}