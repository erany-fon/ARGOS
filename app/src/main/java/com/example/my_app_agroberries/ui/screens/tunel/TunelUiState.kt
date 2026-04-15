package com.example.my_app_agroberries.ui.screens.tunel

import com.example.my_app_agroberries.domain.model.Tunel

data class TunelUiState(
    val tuneles: List<Tunel> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)