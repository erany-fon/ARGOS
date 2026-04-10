package com.example.my_app_agroberries.domain.repository

import com.example.my_app_agroberries.domain.model.TipoPlaga
import kotlinx.coroutines.flow.Flow

interface TipoPlagaRepository {

    // lista completa de plagas disponibles
    fun getAllTiposPlaga(): Flow<List<TipoPlaga>>

    suspend fun guardarTiposPlaga(tipos: List<TipoPlaga>)
}