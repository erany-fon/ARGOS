package com.example.my_app_agroberries.domain.repository

import com.example.my_app_agroberries.domain.model.Plaga
import kotlinx.coroutines.flow.Flow

interface PlagaRepository {
    suspend fun insertPlaga(plaga: Plaga)

    suspend fun insertAll(plagas: List<Plaga>)

    suspend fun getPlagaById(id: Int): Plaga?

    fun getAllPlagas(): Flow<List<Plaga>>

    suspend fun clearPlagas()
}

