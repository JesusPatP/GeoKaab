package com.app.geokaab.ui.type_experiences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.geokaab.data.model.Experience
import com.app.geokaab.data.model.Location
import com.app.geokaab.data.model.TypeExperience
import com.app.geokaab.data.repository.ExperienceRepository
import com.app.geokaab.data.repository.LocationRepository
import com.app.geokaab.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LocationViewModel@Inject constructor(
    val repository: LocationRepository
): ViewModel() {

    private val _Locations = MutableLiveData<UiState<List<Location>>>()
    val Location: LiveData<UiState<List<Location>>>
        get() = _Locations

    fun getLocations() {
        _Locations.value = UiState.Loading
        repository.getLocations { _Locations.value = it }
    }


}