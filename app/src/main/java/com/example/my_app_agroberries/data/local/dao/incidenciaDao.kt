package com.example.my_app_agroberries.data.local.dao

import androidx.room.*
import com.example.my_app_agroberries.data.local.entity.IncidenciaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidenciaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncidencia(incidencia: IncidenciaEntity)

    // historial del usuario
    @Query("SELECT * FROM incidencias WHERE idUsuario = :idUsuario ORDER BY fecha DESC")
    fun getIncidenciasByUsuario(idUsuario: Int): Flow<List<IncidenciaEntity>>

    // las que aun no se han enviado al servidor
    @Query("SELECT * FROM incidencias WHERE sincronizado = 0")
    suspend fun getPendientesSincronizar(): List<IncidenciaEntity>

    // marcar como sincronizada cuando llegue al servidor
    @Query("UPDATE incidencias SET sincronizado = 1 WHERE idIncidencia = :id")
    suspend fun marcarSincronizada(id: Int)

    @Query("DELETE FROM incidencias")
    suspend fun clearIncidencias()
}