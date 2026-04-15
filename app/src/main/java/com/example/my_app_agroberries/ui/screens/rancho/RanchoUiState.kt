package com.example.my_app_agroberries.ui.screens.rancho

import com.example.my_app_agroberries.domain.model.Rancho

data class RanchoUiState(
    val ranchos: List<Rancho> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)