package com.desi.expertplantapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Soil (
   val key: String? = null,
   val name: String? = null,
   val desc: String? = null,
   val image: String? = null
) : Parcelable