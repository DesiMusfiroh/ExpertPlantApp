package com.desi.expertplantapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.desi.expertplantapp.data.DataRepository
import com.desi.expertplantapp.data.Soil

class HomeViewModel : ViewModel() {
    fun getSoils(): LiveData<ArrayList<Soil>> {
        val dataRepository = DataRepository()
        return dataRepository.getSoilsData()
    }

}