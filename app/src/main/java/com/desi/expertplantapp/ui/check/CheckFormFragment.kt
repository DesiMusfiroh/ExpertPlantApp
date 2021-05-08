package com.desi.expertplantapp.ui.check

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import com.desi.expertplantapp.data.Soil
import com.desi.expertplantapp.databinding.FragmentCheckFormBinding
import com.google.firebase.database.*

class CheckFormFragment : Fragment() {

    private lateinit var fragmentCheckFormBinding: FragmentCheckFormBinding
    private lateinit var database: DatabaseReference
    private var arrayAdapter: ArrayAdapter<String> ? = null
    private var listSoils = arrayOf("tanah liat", "tanah gambut", "tanah aluvial")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCheckFormBinding = FragmentCheckFormBinding.inflate(layoutInflater, container, false)
        return fragmentCheckFormBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, listSoils) }
        fragmentCheckFormBinding.autoCompleteTextView.setAdapter(arrayAdapter)

        getListSoils()
    }

    private fun getListSoils() {
        database = FirebaseDatabase.getInstance().getReference("soils")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (soilSnapshot in snapshot.children) {

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("firebase", "gagal")
            }
        })
    }
}