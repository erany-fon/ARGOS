package com.example.my_app_agroberries.ui.screens.surco
import com.example.my_app_agroberries.domain.model.Surco

data class SurcoUiState (
    val surcos: List<Surco> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)