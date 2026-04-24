package com.example.my_app_agroberries.data.repository

import com.example.my_app_agroberries.data.local.dao.PlagaDao
import com.example.my_app_agroberries.data.local.mapper.toDomain
import com.example.my_app_agroberries.data.local.mapper.toEntity
import com.example.my_app_agroberries.domain.model.Plaga
import com.example.my_app_agroberries.domain.repository.PlagaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlagaRepositoryImpl @Inject constructor(
    private val plagaDao: PlagaDao
) : PlagaRepository {

    override suspend fun insertPlaga(plaga: Plaga) {
        plagaDao.insertPlaga(plaga.toEntity())
    }

    override suspend fun insertAll(plagas: List<Plaga>) {
        plagaDao.insertAll(plagas.map { it.toEntity() })
    }

    override suspend fun getPlagaById(id: Int): Plaga? {
        return plagaDao.getPlagaById(id)?.toDomain()
    }

    override fun getAllPlagas(): Flow<List<Plaga>> {
        return plagaDao.getAllPlagas().map { plagas ->
            plagas.map { it.toDomain() }
        }
    }

    override suspend fun clearPlagas() {
        plagaDao.clearPlagas()
    }
}
