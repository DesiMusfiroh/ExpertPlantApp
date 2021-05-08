package com.desi.expertplantapp.ui.check

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.desi.expertplantapp.databinding.FragmentCheckFormBinding
import com.google.firebase.database.*

class CheckFormFragment : Fragment() {

    private lateinit var fragmentCheckFormBinding: FragmentCheckFormBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCheckFormBinding = FragmentCheckFormBinding.inflate(layoutInflater, container, false)
        return fragmentCheckFormBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListSoils()
    }

    private fun getListSoils() {
        database = FirebaseDatabase.getInstance().getReference("soils")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("firebase", "gagal")
            }
        })
    }
}