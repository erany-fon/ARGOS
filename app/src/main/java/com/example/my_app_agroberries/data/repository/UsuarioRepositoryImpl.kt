package com.example.my_app_agroberries.data.repository
import com.example.my_app_agroberries.data.local.dao.UsuarioDao
import com.example.my_app_agroberries.data.local.mapper.toDomain
import com.example.my_app_agroberries.data.local.mapper.toEntity
import com.example.my_app_agroberries.domain.model.Usuario
import com.example.my_app_agroberries.domain.repository.UsuarioRepository
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val usuarioDao: UsuarioDao
    ) : UsuarioRepository {

    override suspend fun login(identificador: String, contrasena: String): Usuario? {
        // en producción aquí harías el hash de la contraseña
        val entity = if (identificador.contains("@")) {
            usuarioDao.loginConEmail(identificador, contrasena)
        } else {
            usuarioDao.loginConNombre(identificador, contrasena)
        }
        return entity?.toDomain()
    }

    override suspend fun guardarUsuario(usuario: Usuario, hash: String) {
        usuarioDao.insertUsuario(usuario.toEntity(hash))
    }

    override suspend fun getUsuarioActivo(idUsuario: Int): Usuario? {
        return usuarioDao.getUsuarioById(idUsuario)?.toDomain()
    }

    override suspend fun cerrarSesion() {
        usuarioDao.clearUsuarios()
    }
}