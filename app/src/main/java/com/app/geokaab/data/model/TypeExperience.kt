package com.app.geokaab.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TypeExperience(
    var id: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("image") val image: String = ""
) : Parcelable