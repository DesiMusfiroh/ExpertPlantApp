package com.desi.expertplantapp.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface PlantDao {
    @Insert
    fun insert(favoritePlant: FavoritePlant)
}