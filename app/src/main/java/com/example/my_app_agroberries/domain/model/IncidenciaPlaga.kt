package com.example.my_app_agroberries.domain.model

data class IncidenciaPlaga(
    val idIncidencia: Int,
    val idSurco: Int,
    val idTipoPlaga: Int,
    val idUsuario: Int,
    val cantidadPlaga: Int,
    val fecha: Long,
    val comentarios: String,
    val fotoUrl: String,
    val sincronizado: Boolean = false
)