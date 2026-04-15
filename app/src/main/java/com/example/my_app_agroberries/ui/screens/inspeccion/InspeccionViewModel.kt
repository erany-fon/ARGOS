package com.example.my_app_agroberries.ui.screens.inspeccion
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_agroberries.domain.model.IncidenciaPlaga
import com.example.my_app_agroberries.domain.model.TipoPlaga
import com.example.my_app_agroberries.domain.repository.TipoPlagaRepository
import com.example.my_app_agroberries.domain.usecase.incidencia.GuardarIncidenciaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspeccionViewModel @Inject constructor(
    private val guardarIncidencia: GuardarIncidenciaUseCase,
    private val tipoPlagaRepository: TipoPlagaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InspeccionUiState())
    val uiState: StateFlow<InspeccionUiState> = _uiState

    init {
        cargarTiposPlagas()
    }

    private fun cargarTiposPlagas() {
        viewModelScope.launch {
            tipoPlagaRepository.getAllTiposPlaga().collect { tipos ->
                _uiState.update { it.copy(tiposPlagas = tipos) }
            }
        }
    }

    fun onTipoPlagaSeleccionado(tipo: TipoPlaga) {
        _uiState.update {
            it.copy(
                tipoPlagaSeleccionado = tipo,
                error = null
            )
        }
    }

    fun onCantidadChange(value: String) {
        // solo acepta números positivos
        if (value.all { it.isDigit() }) {
            _uiState.update { it.copy(cantidad = value, error = null) }
        }
    }

    fun onComentariosChange(value: String) {
        _uiState.update { it.copy(comentarios = value) }
    }

    fun guardar(idSurco: Int, idUsuario: Int) {
        viewModelScope.launch {
            val estado = _uiState.value

            val incidencia = IncidenciaPlaga(
                idIncidencia = 0,
                idSurco = idSurco,
                idTipoPlaga = estado.tipoPlagaSeleccionado?.idTipoPlaga ?: 0,
                idUsuario = idUsuario,
                cantidadPlaga = estado.cantidad.toIntOrNull() ?: 0,
                fecha = System.currentTimeMillis(),
                comentarios = estado.comentarios,
                fotoUrl = "",
                sincronizado = false  // pendiente de sync al servidor
            )

            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = guardarIncidencia(incidencia)

            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            guardadoExitoso = true
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
            )
        }
    }
}