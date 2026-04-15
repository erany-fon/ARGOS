package com.example.my_app_agroberries.ui.screens.inspeccion
import com.example.my_app_agroberries.domain.model.TipoPlaga

data class InspeccionUiState(
    val tiposPlagas: List<TipoPlaga> = emptyList(),
    val tipoPlagaSeleccionado: TipoPlaga? = null,
    val cantidad: String = "",
    val comentarios: String = "",
    val isLoading: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val error: String? = null
)