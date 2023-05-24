package com.app.geokaab.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Location(
    @SerializedName("id") var id: String = "",
    @SerializedName("location") var location: String = "",
    @SerializedName("coordinates") var coordinates: String = "",
) : Serializable