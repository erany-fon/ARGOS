package com.example.my_app_agroberries.ui.screens.surco

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_agroberries.domain.usecase.surco.GetSurcosByTunelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurcoViewModel @Inject constructor(
    private val getSurcosByTunel: GetSurcosByTunelUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SurcoUiState())
    val uiState: StateFlow<SurcoUiState> = _uiState

    fun cargarSurco(idTunel: Int) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                getSurcosByTunel(idTunel).collect { surcos ->
                    _uiState.update {
                        it.copy(surcos = surcos, isLoading = false)
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("SurcoViewModel", "Error cargando surcos", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar surcos: ${e.message}"
                    )
                }
            }

        }
    }
}