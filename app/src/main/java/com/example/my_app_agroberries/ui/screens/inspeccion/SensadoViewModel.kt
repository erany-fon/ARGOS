package com.example.my_app_agroberries.ui.screens.inspeccion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juul.kable.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.UUID
import javax.inject.Inject

data class SensadoUiState(
    val temperatura: Float = 0f,
    val humedad: Float = 0f,
    val isConnected: Boolean = false,
    val mensaje: String = "Buscando sensor ARGOS..."
)

@HiltViewModel
class SensadoViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SensadoUiState())
    val uiState: StateFlow<SensadoUiState> = _uiState

    private var peripheral: Peripheral? = null

    private val SERVICE_UUID = UUID.fromString("0000181a-0000-1000-8000-00805f9b34fb")

    private val tempCharacteristic = characteristicOf(
        service = "0000181a-0000-1000-8000-00805f9b34fb",
        characteristic = "00002a6e-0000-1000-8000-00805f9b34fb"
    )
    private val humCharacteristic = characteristicOf(
        service = "0000181a-0000-1000-8000-00805f9b34fb",
        characteristic = "00002a6f-0000-1000-8000-00805f9b34fb"
    )

    fun iniciarConexion() {
        if (_uiState.value.isConnected) return

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(mensaje = "Iniciando Escaneo BLE...") }

                val scanner = Scanner {
                    filters = listOf(Filter.Service(SERVICE_UUID))
                }

                _uiState.update { it.copy(mensaje = "Buscando túnel ARGOS...") }

                val advertisement = withTimeout(20000L) {
                    scanner.advertisements.first().also { adv ->
                        Log.d("ARGOS_BLE", "¡Dispositivo encontrado!: ${adv.name}")
                    }
                }

                _uiState.update { it.copy(mensaje = "Conectando al R4...") }

                peripheral = viewModelScope.peripheral(advertisement)
                peripheral?.connect()

                _uiState.update { it.copy(isConnected = true, mensaje = "Conectado. Leyendo datos...") }

                observarDatos()

            } catch (e: TimeoutCancellationException) {
                _uiState.update { it.copy(mensaje = "No se encontró el sensor. Revisa el GPS.") }
            } catch (e: Exception) {
                _uiState.update { it.copy(mensaje = "Error: ${e.localizedMessage}") }
            }
        }
    }

    private fun observarDatos() {
        viewModelScope.launch {
            peripheral?.observe(tempCharacteristic)?.collect { data ->
                val valor = decodeBLEValue(data)
                _uiState.update { it.copy(temperatura = valor) }
            }
        }
        viewModelScope.launch {
            peripheral?.observe(humCharacteristic)?.collect { data ->
                val valor = decodeBLEValue(data)
                _uiState.update { it.copy(humedad = valor) }
            }
        }
    }

    private fun decodeBLEValue(data: ByteArray): Float {
        return try {
            val raw = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).short
            raw / 100f
        } catch (e: Exception) { 0f }
    }
}