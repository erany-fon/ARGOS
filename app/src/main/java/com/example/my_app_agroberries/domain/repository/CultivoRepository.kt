package com.example.my_app_agroberries.domain.repository
import com.example.my_app_agroberries.domain.model.Cultivo

interface CultivoRepository {
    suspend fun insertCultivo(cultivo: Cultivo)
    suspend fun insertAll(cultivos: List<Cultivo>)
    suspend fun getCultivoById(idCultivo: Int): Cultivo?
    suspend fun getAllCultivos(): List<Cultivo>
    suspend fun clearCultivos()
}

