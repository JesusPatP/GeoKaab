package com.app.geokaab.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Experience(
    var id: String = "",
    @SerializedName("title") var title: String = "",
    @SerializedName("type") val type: List<String> = arrayListOf(),
    @SerializedName("recommendations") val recommendations : String = "",
    @SerializedName("offers") val offers : List<String> = arrayListOf(),
    @SerializedName("observations") val observations: String = "",
    @SerializedName("location") val location: List<String> = arrayListOf(),
    @SerializedName("images") val images: List<String> = arrayListOf(),
    @SerializedName("description") val description: String = "",
    @SerializedName("capacity") val capacity: Int = 0,
    @SerializedName("availability") val availability : List<String> = arrayListOf(),
    @SerializedName("activities") val activities: List<String> = arrayListOf(),
) : Parcelable