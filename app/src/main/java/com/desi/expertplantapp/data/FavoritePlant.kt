package com.desi.expertplantapp.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "favorite_plants")
data class FavoritePlant (
    @PrimaryKey
    @NonNull
    val id: String,

    @ColumnInfo(name = "key")
    val key: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "species")
    val species: String? = null,

    @ColumnInfo(name = "desc")
    val desc: String? = null,

    @ColumnInfo(name = "benefit")
    val benefit: String? = null,

    @ColumnInfo(name = "min_altitude")
    val min_altitude: String? = null,

    @ColumnInfo(name = "max_altitude")
    val max_altitude: String? = null,

    @ColumnInfo(name = "min_temperature")
    val min_temperature: String? = null,

    @ColumnInfo(name = "max_temperature")
    val max_temperature: String? = null,

    @ColumnInfo(name = "min_humidity")
    val min_humidity: String? = null,

    @ColumnInfo(name = "max_humidity")
    val max_humidity: String? = null,

    @ColumnInfo(name = "min_rainfall")
    val min_rainfall: String? = null,

    @ColumnInfo(name = "max_rainfall")
    val max_rainfall: String? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,

    @ColumnInfo(name = "score")
    var score: Int? = 0,
) : Parcelable