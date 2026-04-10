package com.example.my_app_agroberries.domain.repository
import com.example.my_app_agroberries.domain.model.Usuario

interface UsuarioRepository {
    //
    suspend fun login(identificador: String, contrasena: String): Usuario?

    //recupera el usuario activo guardado en el dispositivo
    suspend fun guardarUsuario(usuario: Usuario, hash: String)

    suspend fun getUsuarioActivo(idUsuario: Int): Usuario?

    //cierra sesion limpiando datos locales
    suspend fun cerrarSesion()
}