package com.example.my_app_agroberries.data.repository

import com.example.my_app_agroberries.data.local.dao.TipoPlagaDao
import com.example.my_app_agroberries.data.local.mapper.toDomain
import com.example.my_app_agroberries.data.local.mapper.toEntity
import com.example.my_app_agroberries.domain.model.TipoPlaga
import com.example.my_app_agroberries.domain.repository.TipoPlagaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TipoPlagaRepositoryImpl @Inject constructor(
    private val tipoPlagaDao: TipoPlagaDao
) : TipoPlagaRepository {

    override fun getAllTiposPlaga(): Flow<List<TipoPlaga>> {
        return tipoPlagaDao.getAllTiposPlaga()
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun guardarTiposPlaga(tipos: List<TipoPlaga>) {
        tipoPlagaDao.insertAll(tipos.map { it.toEntity() })
    }
}