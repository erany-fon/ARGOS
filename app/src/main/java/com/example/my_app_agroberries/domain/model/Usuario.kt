package com.example.my_app_agroberries.domain.model

data class Usuario (
    val idUsuario: Int,
    val idRol: Int,
    val nombre: String,
    val apellidoP: String,
    val apellidoM: String,
    val email: String,
    val usuario: String
)