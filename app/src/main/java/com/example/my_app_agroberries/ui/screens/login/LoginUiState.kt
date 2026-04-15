package com.example.my_app_agroberries.ui.screens.login
// representa todos los estados posibles de la pantalla
data class LoginUiState(
    val identificador: String = "",     // nombre o email
    val contrasena: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val loginExitoso: Boolean = false,
    val idUsuario: Int = 0
)