package com.example.my_app_agroberries.data.local.dao
import androidx.room.*
import com.example.my_app_agroberries.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios WHERE idUsuario = :id")
    suspend fun getUsuarioById(id: Int): UsuarioEntity?

    @Query("""
        SELECT * FROM usuarios 
        WHERE nombre = :nombre 
        AND contrasenaHash = :hash 
        LIMIT 1
    """)
    suspend fun loginConNombre(nombre: String, hash: String): UsuarioEntity?

    @Query("""
        SELECT * FROM usuarios 
        WHERE email = :email 
        AND contrasenaHash = :hash 
        LIMIT 1
    """)
    suspend fun loginConEmail(email: String, hash: String): UsuarioEntity?

    @Query("DELETE FROM usuarios")
    suspend fun clearUsuarios()
}
