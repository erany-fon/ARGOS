package com.example.my_app_agroberries.domain.usecase.incidencia

import com.example.my_app_agroberries.domain.repository.DetalleIncidenciaRepository
import javax.inject.Inject

class ObtenerTotalPlagasIncidenciaUseCase @Inject constructor(
    private val repository: DetalleIncidenciaRepository
) {
    suspend operator fun invoke(idIncidencia: Int): Result<Int> {
        return try {
            if (idIncidencia <= 0) {
                return Result.failure(Exception("ID de incidencia inválido"))
            }
            val total = repository.getTotalCantidadPlagas(idIncidencia)
            Result.success(total)
        } catch (e: Exception) {
            Result.failure(Exception("Error al obtener total de plagas: ${e.message}"))
        }
    }
}

