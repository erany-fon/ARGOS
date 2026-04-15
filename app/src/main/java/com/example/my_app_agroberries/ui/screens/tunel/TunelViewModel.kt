package com.example.my_app_agroberries.ui.screens.tunel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_agroberries.domain.usecase.tunel.GetTunelesByRanchoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TunelViewModel @Inject constructor(
    private val getTunelesByRancho: GetTunelesByRanchoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TunelUiState())
    val uiState: StateFlow<TunelUiState> = _uiState

    fun cargarTuneles(idRancho: Int) {
        viewModelScope.launch {
            getTunelesByRancho(idRancho).collect { tuneles ->
                _uiState.update {
                    it.copy(tuneles = tuneles, isLoading = false)
                }
            }
        }
    }
}