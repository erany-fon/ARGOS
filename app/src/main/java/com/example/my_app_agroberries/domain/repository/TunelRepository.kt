package com.example.my_app_agroberries.domain.repository
import com.example.my_app_agroberries.domain.model.Tunel
import kotlinx.coroutines.flow.Flow

interface TunelRepository {

    // trae tuneles de un rancho específico
    fun getTunelesByRancho(idRancho: Int): Flow<List<Tunel>>

    suspend fun guardarTuneles(tuneles: List<Tunel>)
}