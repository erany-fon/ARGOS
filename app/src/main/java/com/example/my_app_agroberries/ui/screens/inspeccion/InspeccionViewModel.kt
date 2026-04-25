package com.example.my_app_agroberries.ui.screens.inspeccion
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_agroberries.domain.model.Incidencia
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
            try {
                tipoPlagaRepository.getAllTiposPlaga().collect { tipos ->
                    _uiState.update { it.copy(tiposPlagas = tipos) }
                }
            } catch (e: Exception) {
                android.util.Log.e("InspeccionViewModel", "Error cargando plagas", e)
                _uiState.update { 
                    it.copy(error = "Error al cargar tipos de plagas")
                }
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
        if (value.isEmpty() || value.all { it.isDigit() }) {
            _uiState.update { it.copy(cantidad = value, error = null) }
        }
    }

    fun onComentariosChange(value: String) {
        _uiState.update { it.copy(comentarios = value) }
    }

    fun guardar(idSurco: Int, idUsuario: Int) {
        viewModelScope.launch {
            try {
                val estado = _uiState.value

                if (estado.tipoPlagaSeleccionado == null) {
                    _uiState.update { it.copy(error = "Selecciona tipo de plaga") }
                    return@launch
                }

                if (estado.cantidad.isEmpty() || estado.cantidad.toIntOrNull() == null) {
                    _uiState.update { it.copy(error = "Cantidad inválida") }
                    return@launch
                }

                val incidencia = Incidencia(
                    idIncidencia = 0,
                    idSurco = idSurco,
                    idTipoPlaga = estado.tipoPlagaSeleccionado.idTipoPlaga,
                    idUsuario = idUsuario,
                    nivelAlerta = 2,
                    fecha = System.currentTimeMillis(),
                    comentarios = estado.comentarios,
                    fotoUrl = "",
                    sincronizado = false
                )

                _uiState.update { it.copy(isLoading = true, error = null) }

                val result = guardarIncidencia(incidencia)

                result.fold(
                    onSuccess = { idGuardado ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                guardadoExitoso = true,
                                cantidad = "",
                                comentarios = ""
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "Error al guardar incidencia"
                            )
                        }
                    }
                )
            } catch (e: Exception) {
                android.util.Log.e("InspeccionViewModel", "Error guardando incidencia", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error inesperado: ${e.message}"
                    )
                }
            }
        }
    }
}