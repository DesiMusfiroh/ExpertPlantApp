package com.desi.expertplantapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantDao {
    @Insert
    fun insert(favoritePlant: FavoritePlant)

    @Query("SELECT * FROM favorite_plants")
    fun getFavoritePlants(): LiveData<List<FavoritePlant>>

    @Query("SELECT count(*) FROM favorite_plants WHERE favorite_plants.`key` = :key")
    suspend fun checkFavorite(key: String): Int

    @Query("DELETE FROM favorite_plants WHERE favorite_plants.`key` = :key")
    suspend fun deleteFromFavorite(key: String): Int

}