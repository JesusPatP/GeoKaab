package com.app.geokaab.data.repository

import com.app.geokaab.data.model.Experience
import com.app.geokaab.data.model.Location
import com.app.geokaab.data.model.TypeExperience
import com.app.geokaab.util.UiState

interface LocationRepository {

    fun getLocations(result: (UiState<List<Location>>) -> Unit)


}