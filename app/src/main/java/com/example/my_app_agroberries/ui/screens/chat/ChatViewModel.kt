package com.example.my_app_agroberries.ui.screens.chat

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Message(
    val text: String,
    val isUser: Boolean,
    val time: String = ""
)

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow(listOf(
        Message("¡Hola! Soy el asistente de ARGOS. Estoy analizando los sensores en tiempo real. ¿En qué te ayudo hoy?", false, getCurrentTime())
    ))
    val messages: StateFlow<List<Message>> = _messages

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        // 1. Añadir mensaje del usuario
        _messages.update { it + Message(text, true, getCurrentTime()) }

        // 2. Lógica de respuesta del Bot (Convertimos a minúsculas para buscar más fácil)
        val input = text.lowercase()

        val response = when {
            // REPORTE GENERAL
            input.contains("reporte") || input.contains("estado") -> {
                "📊 *Reporte General:*\n- Salud Global: 92%\n- Temp. Media: 24.5°C\n- Humedad: 68%\n- Sensores R4: 12/12 (En Línea)\nTodo se mantiene estable."
            }

            // PLAGAS
            input.contains("plaga") || input.contains("plagas") || input.contains("insectos") -> {
                "🐛 *Alerta de Temporada:*\nActualmente estamos en época de altas temperaturas, lo que favorece a la **Araña Roja**. Te sugiero revisar minuciosamente el envés de las hojas en los túneles más secos (Túnel 2 y 4)."
            }

            // PREVENCIÓN Y SUGERENCIAS
            input.contains("sugerencia") || input.contains("prevención") || input.contains("preventiva") || input.contains("recomiendas") -> {
                "💡 *Sugerencia Preventiva:*\nNoté que la humedad superó el 82% esta madrugada en el Túnel 1. Te recomiendo **abrir las cortinas laterales** hoy al mediodía para ventilar y prevenir hongos como la Botrytis."
            }

            // RIEGO
            input.contains("riego") || input.contains("agua") || input.contains("regar") -> {
                "💧 *Estado de Riego:*\nLa humedad del suelo se reporta en 65%. Es un nivel óptimo para las berries. El próximo ciclo de riego automatizado está programado para las 18:00 hrs."
            }

            // PREGUNTAS SOBRE TÚNELES ESPECÍFICOS
            input.contains("tunel 1") || input.contains("túnel 1") -> {
                "🔍 *Túnel 1:*\n- Temp: 26°C\n- Humedad: 82% (¡Ligeramente alta!)\n- Estado: Alerta preventiva activada por humedad."
            }

            // SALUDOS Y AYUDA
            input.contains("hola") || input.contains("buenos días") || input.contains("buenas tardes") -> {
                "¡Hola! ¿Qué tal la jornada en el rancho? Pregúntame por el reporte, estado de plagas o si necesitas una sugerencia preventiva."
            }
            input.contains("ayuda") || input.contains("qué puedes hacer") -> {
                "Soy ARGOS. Puedo responderte sobre:\n1. Reporte general\n2. Alertas de plagas\n3. Sugerencias de riego y ventilación\n4. Datos específicos de un túnel."
            }
            input.contains("gracias") || input.contains("excelente") -> {
                "¡De nada! Aquí estoy monitoreando 24/7. ¡Que tengas buena cosecha!"
            }

            // DEFAULT (Cuando no entiende la pregunta)
            else -> {
                "Lo siento, mis algoritmos aún están procesando ese tipo de consultas. Intenta preguntarme por 'sugerencias', 'plagas', 'riego' o el 'reporte general'."
            }
        }

        // Simular un pequeñísimo "tiempo de pensar" del bot (opcional, pero se ve bien)
        // Por ahora lo agregamos directo para mantenerlo simple y síncrono
        _messages.update { it + Message(response, false, getCurrentTime()) }
    }

    // Función extra para ponerle la hora real a los mensajes
    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }
}