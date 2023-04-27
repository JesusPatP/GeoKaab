package com.app.geokaab.ui.type_experiences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.geokaab.data.model.Experience
import com.app.geokaab.data.repository.ExperienceRepository
import com.app.geokaab.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExperienceViewModel@Inject constructor(
    val repository: ExperienceRepository
): ViewModel() {

    private val _Experiences = MutableLiveData<UiState<List<Experience>>>()
    val Experience: LiveData<UiState<List<Experience>>>
        get() = _Experiences

    fun getExperiences() {
        _Experiences.value = UiState.Loading
        repository.getExperiences { _Experiences.value = it }
    }



}