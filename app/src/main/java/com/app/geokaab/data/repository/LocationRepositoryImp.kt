package com.app.geokaab.data.repository

import com.app.geokaab.data.model.Experience
import com.app.geokaab.data.model.Location
import com.app.geokaab.data.model.TypeExperience
import com.app.geokaab.util.FireStoreCollection
import com.app.geokaab.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class LocationRepositoryImp(
    val database: FirebaseFirestore,
    val storageReference: StorageReference
) :  LocationRepository {



    override fun getLocations(result: (UiState<List<Location>>) -> Unit) {
        //Referencia de la coleccion de firestore
        database.collection(FireStoreCollection.Location)
            .get()
            .addOnSuccessListener {
                val Locations = arrayListOf<Location>()
                for (document in it) {
                    val Location = document.toObject(Location::class.java)
                    Locations.add(Location)
                }
                result.invoke(
                    UiState.Success(Locations)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

}
