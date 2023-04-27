package com.app.geokaab.data.repository

import com.app.geokaab.data.model.TypeExperience
import com.app.geokaab.util.UiState

interface TypeExperienceRepository {

    fun getTypeExperiences(result: (UiState<List<TypeExperience>>) -> Unit)

}