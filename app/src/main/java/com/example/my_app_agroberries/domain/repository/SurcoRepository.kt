package com.example.my_app_agroberries.domain.repository
import com.example.my_app_agroberries.domain.model.Surco
import kotlinx.coroutines.flow.Flow

interface SurcoRepository {

    // trae surcos de un tunel específico
    fun getSurcosByTunel(idTunel: Int): Flow<List<Surco>>

    suspend fun guardarSurcos(surcos: List<Surco>)
}