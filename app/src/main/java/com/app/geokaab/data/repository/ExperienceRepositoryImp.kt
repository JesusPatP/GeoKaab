package com.app.geokaab.data.repository

import com.app.geokaab.data.model.Experience
import com.app.geokaab.data.model.TypeExperience
import com.app.geokaab.util.FireStoreCollection
import com.app.geokaab.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class ExperienceRepositoryImp(
    val database: FirebaseFirestore,
    val storageReference: StorageReference
) : ExperienceRepository {

    override fun getExperiences(result: (UiState<List<Experience>>) -> Unit) {
        //Referencia de la coleccion de firestore
        database.collection(FireStoreCollection.Experience)
            .get()
            .addOnSuccessListener {
                val Experiences = arrayListOf<Experience>()
                for (document in it) {
                    val Experience = document.toObject(Experience::class.java)
                    Experiences.add(Experience)
                }
                result.invoke(
                    UiState.Success(Experiences)
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