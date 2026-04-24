package com.example.my_app_agroberries.data.local.dao

import androidx.room.*
import com.example.my_app_agroberries.data.local.entity.IncidenciaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidenciaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncidencia(incidencia: IncidenciaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(incidencias: List<IncidenciaEntity>)

    @Query("SELECT * FROM incidencias WHERE idUsuario = :idUsuario ORDER BY fecha DESC")
    fun getIncidenciasByUsuario(idUsuario: Int): Flow<List<IncidenciaEntity>>

    @Query("SELECT * FROM incidencias WHERE idIncidencia = :id")
    suspend fun getIncidenciaById(id: Int): IncidenciaEntity?

    @Query("SELECT * FROM incidencias WHERE sincronizado = 0 ORDER BY fecha ASC")
    suspend fun getPendientesSincronizar(): List<IncidenciaEntity>


    @Query("UPDATE incidencias SET sincronizado = 1 WHERE idIncidencia = :idIncidencia")
    suspend fun marcarSincronizada(idIncidencia: Int)

    @Query("DELETE FROM incidencias")
    suspend fun clearIncidencias()
}