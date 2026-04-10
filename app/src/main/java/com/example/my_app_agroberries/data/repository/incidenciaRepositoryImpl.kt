package com.example.my_app_agroberries.data.repository

import com.example.my_app_agroberries.data.local.dao.IncidenciaDao
import com.example.my_app_agroberries.data.local.mapper.toDomain
import com.example.my_app_agroberries.data.local.mapper.toEntity
import com.example.my_app_agroberries.domain.model.IncidenciaPlaga
import com.example.my_app_agroberries.domain.repository.IncidenciaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IncidenciaRepositoryImpl @Inject constructor(
    private val incidenciaDao: IncidenciaDao
) : IncidenciaRepository {

    override suspend fun guardarIncidencia(incidencia: IncidenciaPlaga) {
        incidenciaDao.insertIncidencia(incidencia.toEntity())
    }

    override fun getIncidenciasByUsuario(idUsuario: Int): Flow<List<IncidenciaPlaga>> {
        return incidenciaDao.getIncidenciasByUsuario(idUsuario)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getPendientesSincronizar(): List<IncidenciaPlaga> {
        return incidenciaDao.getPendientesSincronizar()
            .map { it.toDomain() }
    }

    override suspend fun marcarSincronizada(idIncidencia: Int) {
        incidenciaDao.marcarSincronizada(idIncidencia)
    }
}