package com.app.geokaab.data.repository

import com.app.geokaab.data.model.Experience
import com.app.geokaab.data.model.TypeExperience
import com.app.geokaab.util.UiState

interface ExperienceRepository {

    fun getExperiences(result: (UiState<List<Experience>>) -> Unit)

    fun filterExperiences(idTypeExperience: String?, result: (UiState<List<Experience>>) -> Unit)

}