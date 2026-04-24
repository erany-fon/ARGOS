package com.example.my_app_agroberries.domain.model

data class Incidencia(
    val idIncidencia: Int = 0,
    val idSurco: Int,
    val idTipoPlaga: Int,
    val idUsuario: Int,
    val fecha: Long,
    val nivelAlerta: Int,
    val comentarios: String,
    val fotoUrl: String,
    val sincronizado: Boolean = false
)