package com.example.my_app_agroberries.domain.model

import java.sql.Timestamp


data class Incidencia_plaga(
    val id_incidencia: Int,
    val id_surco: Surco,
    val id_tipo_plaga: Tipo_plaga,
    val id_usuario: Usuario,
    val cantidad_plaga: Int,
    val fecha: Timestamp,
    val comentarios: String,
    val foto_url: String
)