package com.example.my_app_agroberries.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class DashboardViewModel : ViewModel() {

    // Estado que la pantalla va a observar para mostrar las alertas
    private val _notificacionActual = MutableStateFlow("")
    val notificacionActual: StateFlow<String> = _notificacionActual

    // Lista de mensajes aleatorios que pediste
    private val mensajes = listOf(
        "⚠️ No has hecho tu revisión diaria.",
        "💧 El túnel 1 tuvo una pérdida de humedad importante.",
        "🐛 Revisión de plagas pendiente en Parcela 3.",
        "✅ Temperatura óptima en todos los sectores.",
        "🔋 Batería baja en el sensor del Túnel 2.",
        "🌱 ¡Buen trabajo! Todas las parcelas están estables."
    )

    fun generarNotificacionAleatoria() {
        // Selecciona un mensaje al azar de la lista
        _notificacionActual.value = mensajes[Random.nextInt(mensajes.size)]
    }

    fun limpiarNotificacion() {
        // Limpiamos el mensaje para que el sistema permita mostrar otro igual si toca la casualidad
        _notificacionActual.value = ""
    }
}