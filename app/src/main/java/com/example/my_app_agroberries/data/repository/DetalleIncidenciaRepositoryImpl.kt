package com.example.my_app_agroberries.data.repository

import com.example.my_app_agroberries.data.local.dao.DetalleIncidenciaDao
import com.example.my_app_agroberries.data.local.mapper.toDomain
import com.example.my_app_agroberries.data.local.mapper.toEntity
import com.example.my_app_agroberries.domain.model.DetalleIncidencia
import com.example.my_app_agroberries.domain.repository.DetalleIncidenciaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DetalleIncidenciaRepositoryImpl @Inject constructor(
    private val detalleIncidenciaDao: DetalleIncidenciaDao
) : DetalleIncidenciaRepository {

    override suspend fun insertDetalleIncidencia(detalleIncidencia: DetalleIncidencia) {
        detalleIncidenciaDao.insertDetalleIncidencia(detalleIncidencia.toEntity())
    }

    override suspend fun insertAll(detalles: List<DetalleIncidencia>) {
        detalleIncidenciaDao.insertAll(detalles.map { it.toEntity() })
    }

    override suspend fun getDetalleIncidenciaById(id: Int): DetalleIncidencia? {
        return detalleIncidenciaDao.getDetalleIncidenciaById(id)?.toDomain()
    }

    override fun getDetallesByIncidencia(idIncidencia: Int): Flow<List<DetalleIncidencia>> {
        return detalleIncidenciaDao.getDetallesByIncidencia(idIncidencia)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getTotalCantidadPlagas(idIncidencia: Int): Int {
        return detalleIncidenciaDao.getTotalCantidadPlagasByIncidencia(idIncidencia)
    }

    override suspend fun getCantidadPlagaByTipo(idIncidencia: Int, idTipoPlaga: Int): Int {
        return detalleIncidenciaDao.getCantidadPlagaByIncidenciaTipo(idIncidencia, idTipoPlaga) ?: 0
    }

    override suspend fun deleteByIncidencia(idIncidencia: Int) {
        detalleIncidenciaDao.deleteByIncidencia(idIncidencia)
    }

    override suspend fun clearDetalles() {
        detalleIncidenciaDao.clearDetalles()
    }
}
