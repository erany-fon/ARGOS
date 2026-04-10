package com.example.my_app_agroberries.data.repository

import com.example.my_app_agroberries.data.local.dao.SurcoDao
import com.example.my_app_agroberries.data.local.mapper.toDomain
import com.example.my_app_agroberries.data.local.mapper.toEntity
import com.example.my_app_agroberries.domain.model.Surco
import com.example.my_app_agroberries.domain.repository.SurcoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SurcoRepositoryImpl @Inject constructor(
    private val surcoDao: SurcoDao
) : SurcoRepository {

    override fun getSurcosByTunel(idTunel: Int): Flow<List<Surco>> {
        return surcoDao.getSurcosByTunel(idTunel)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun guardarSurcos(surcos: List<Surco>) {
        surcoDao.insertAll(surcos.map { it.toEntity() })
    }
}