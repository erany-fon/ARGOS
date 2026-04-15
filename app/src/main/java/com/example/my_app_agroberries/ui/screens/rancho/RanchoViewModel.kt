package com.example.my_app_agroberries.ui.screens.rancho

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_agroberries.domain.usecase.rancho.GetRanchosByUsuarioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RanchoViewModel @Inject constructor(
    private val getRanchosByUsuario: GetRanchosByUsuarioUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RanchoUiState())
    val uiState: StateFlow<RanchoUiState> = _uiState

    fun cargarRanchos(idUsuario: Int) {
        viewModelScope.launch {
            getRanchosByUsuario(idUsuario).collect { ranchos ->
                _uiState.update {
                    it.copy(
                        ranchos = ranchos,
                        isLoading = false
                    )
                }
            }
        }
    }
}