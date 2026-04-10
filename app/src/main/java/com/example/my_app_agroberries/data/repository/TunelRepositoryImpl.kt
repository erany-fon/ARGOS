package com.example.my_app_agroberries.data.repository

import com.example.my_app_agroberries.data.local.dao.TunelDao
import com.example.my_app_agroberries.data.local.mapper.toDomain
import com.example.my_app_agroberries.data.local.mapper.toEntity
import com.example.my_app_agroberries.domain.model.Tunel
import com.example.my_app_agroberries.domain.repository.TunelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TunelRepositoryImpl @Inject constructor(
    private val tunelDao: TunelDao
) : TunelRepository {

    override fun getTunelesByRancho(idRancho: Int): Flow<List<Tunel>> {
        return tunelDao.getTunelesByRancho(idRancho)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun guardarTuneles(tuneles: List<Tunel>) {
        tunelDao.insertAll(tuneles.map { it.toEntity() })
    }
}