package com.example.cityguide.ui

import androidx.lifecycle.ViewModel
import com.example.cityguide.data.Category
import com.example.cityguide.data.DemoRepository
import com.example.cityguide.data.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val selectedPlace: Place? = null
)

class CityViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        UiState(categories = DemoRepository.categories)
    )
    val state: StateFlow<UiState> = _state

    fun selectCategory(id: String) {
        _state.update { it.copy(selectedCategory = DemoRepository.getCategory(id), selectedPlace = null) }
    }

    fun selectPlace(categoryId: String, placeId: String) {
        _state.update { it.copy(selectedPlace = DemoRepository.getPlace(categoryId, placeId)) }
    }

    fun clearSelection() {
        _state.update { it.copy(selectedPlace = null) }
    }
}
