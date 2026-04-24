package com.example.my_app_agroberries.domain.repository

import com.example.my_app_agroberries.domain.model.DetalleIncidencia
import kotlinx.coroutines.flow.Flow

interface DetalleIncidenciaRepository {
    suspend fun insertDetalleIncidencia(detalleIncidencia: DetalleIncidencia)

    suspend fun insertAll(detalles: List<DetalleIncidencia>)

    suspend fun getDetalleIncidenciaById(id: Int): DetalleIncidencia?

    // Obtener detalles de una incidencia específica
    fun getDetallesByIncidencia(idIncidencia: Int): Flow<List<DetalleIncidencia>>

    // Sumar cantidad total de plagas de una incidencia
    suspend fun getTotalCantidadPlagas(idIncidencia: Int): Int

    // Obtener cantidad de plagas por tipo en una incidencia
    suspend fun getCantidadPlagaByTipo(idIncidencia: Int, idTipoPlaga: Int): Int

    suspend fun deleteByIncidencia(idIncidencia: Int)

    suspend fun clearDetalles()
}

