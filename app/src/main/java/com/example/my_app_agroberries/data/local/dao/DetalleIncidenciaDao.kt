package com.example.my_app_agroberries.data.local.dao

import androidx.room.*
import com.example.my_app_agroberries.data.local.entity.DetalleIncidenciasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DetalleIncidenciaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetalleIncidencia(detalleIncidencia: DetalleIncidenciasEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(detalles: List<DetalleIncidenciasEntity>)

    @Query("SELECT * FROM DetalleIncidencias WHERE idDetalleIncidencia = :id")
    suspend fun getDetalleIncidenciaById(id: Int): DetalleIncidenciasEntity?

    // Obtener todos los detalles de una incidencia
    @Query("SELECT * FROM DetalleIncidencias WHERE idIncidencia = :idIncidencia")
    fun getDetallesByIncidencia(idIncidencia: Int): Flow<List<DetalleIncidenciasEntity>>

    // Sumar cantidad total de plagas de una incidencia
    @Query("SELECT COALESCE(SUM(cantidadPlaga), 0) FROM DetalleIncidencias WHERE idIncidencia = :idIncidencia")
    suspend fun getTotalCantidadPlagasByIncidencia(idIncidencia: Int): Int

    // Obtener cantidad de plagas por tipo en una incidencia
    @Query("SELECT SUM(cantidadPlaga) FROM DetalleIncidencias WHERE idIncidencia = :idIncidencia AND idTipoPlaga = :idTipoPlaga")
    suspend fun getCantidadPlagaByIncidenciaTipo(idIncidencia: Int, idTipoPlaga: Int): Int?

    @Query("DELETE FROM DetalleIncidencias WHERE idIncidencia = :idIncidencia")
    suspend fun deleteByIncidencia(idIncidencia: Int)

    @Query("DELETE FROM DetalleIncidencias")
    suspend fun clearDetalles()
}
