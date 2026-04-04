package com.example.my_app_agroberries.domain.model

data class Usuario (
    val id_usuario: Int,
    val nombre: String,
    val apellido: String,
    val contraseña: String,
    val rol: String
)