package com.example.my_app_agroberries.domain.usecase.auth

import com.example.my_app_agroberries.domain.model.Usuario
import com.example.my_app_agroberries.domain.repository.UsuarioRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UsuarioRepository
) {
    // operator invoke -> se llama como loginUseCase(identificador, contrasena)
    suspend operator fun invoke(
        identificador: String,
        contrasena: String
    ): Result<Usuario> {

        // validaciones antes de ir a la BD
        if (identificador.isBlank()) {
            return Result.failure(Exception("El nombre o email no puede estar vacío"))
        }
        if (contrasena.isBlank()) {
            return Result.failure(Exception("La contraseña no puede estar vacía"))
        }
        if (contrasena.length < 4) {
            return Result.failure(Exception("La contraseña debe tener al menos 4 caracteres"))
        }

        // intenta el login en el repositorio
        return try {
            val usuario = repository.login(identificador, contrasena)
            if (usuario != null) {
                Result.success(usuario)     // ← login correcto
            } else {
                Result.failure(Exception("Usuario o contraseña incorrectos"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error al iniciar sesión: ${e.message}"))
        }
    }
}