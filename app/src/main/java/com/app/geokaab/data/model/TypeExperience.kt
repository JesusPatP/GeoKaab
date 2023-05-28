package com.app.geokaab.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TypeExperience(
    @SerializedName("id") var id: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("status") val status: Int = 0
) : Parcelable