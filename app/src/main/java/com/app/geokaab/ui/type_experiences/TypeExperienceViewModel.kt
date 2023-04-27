package com.app.geokaab.ui.type_experiences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.geokaab.data.model.TypeExperience
import com.app.geokaab.data.repository.TypeExperienceRepository
import com.app.geokaab.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TypeExperienceViewModel@Inject constructor(
    val repository: TypeExperienceRepository
): ViewModel() {

    private val _typeExperiences = MutableLiveData<UiState<List<TypeExperience>>>()
    val typeExperience: LiveData<UiState<List<TypeExperience>>>
        get() = _typeExperiences

    fun getTypeExperiences() {
        _typeExperiences.value = UiState.Loading
        repository.getTypeExperiences { _typeExperiences.value = it }
    }



}