package com.app.geokaab.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable


data class Experience(
    @SerializedName("id") var id: String = "",
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

    @SerializedName("contacts") val contacts: List<String> = arrayListOf(),
    @SerializedName("id_location") val id_location: List<String> = arrayListOf(),
) : Serializable