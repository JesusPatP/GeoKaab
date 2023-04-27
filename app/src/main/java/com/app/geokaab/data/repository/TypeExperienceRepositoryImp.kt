package com.app.geokaab.data.repository

import com.app.geokaab.data.model.TypeExperience
import com.app.geokaab.util.FireStoreCollection
import com.app.geokaab.util.FireStoreDocumentField
import com.app.geokaab.util.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.StorageReference

class TypeExperienceRepositoryImp(
    val database: FirebaseFirestore,
    val storageReference: StorageReference
) : TypeExperienceRepository {

    override fun getTypeExperiences(result: (UiState<List<TypeExperience>>) -> Unit) {
        //Referencia de la coleccion de firestore
        database.collection(FireStoreCollection.TypeExperience)
            .get()
            .addOnSuccessListener {
                val typeExperiences = arrayListOf<TypeExperience>()
                for (document in it) {
                    val typeExperience = document.toObject(TypeExperience::class.java)
                    typeExperiences.add(typeExperience)
                }
                result.invoke(
                    UiState.Success(typeExperiences)
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