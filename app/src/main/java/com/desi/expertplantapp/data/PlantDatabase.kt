package com.desi.expertplantapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoritePlant::class], version = 1)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    companion object {
        var INSTANCE : PlantDatabase? = null
        fun getDatabase(context: Context) : PlantDatabase?  {
            if (INSTANCE == null) {
                synchronized(PlantDatabase::class) {
                    INSTANCE= Room.databaseBuilder(context.applicationContext,
                            PlantDatabase::class.java, "plant_database").build()
                }
            }
            return INSTANCE
        }
    }
}