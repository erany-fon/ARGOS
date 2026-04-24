package com.example.my_app_agroberries.domain.usecase.incidencia

import com.example.my_app_agroberries.domain.model.DetalleIncidencia
import com.example.my_app_agroberries.domain.repository.DetalleIncidenciaRepository
import javax.inject.Inject

class GuardarDetalleIncidenciaUseCase @Inject constructor(
    private val repository: DetalleIncidenciaRepository
) {
    suspend operator fun invoke(detalle: DetalleIncidencia): Result<Unit> {

        // validaciones
        if (detalle.idDetalleIncidencia <= 0) {
            return Result.failure(Exception("ID de incidencia inválido"))
        }
        if (detalle.idTipoPlaga <= 0) {
            return Result.failure(Exception("Debes seleccionar un tipo de plaga"))
        }
        if (detalle.idPlaga <= 0) {
            return Result.failure(Exception("Debes seleccionar una plaga"))
        }
        if (detalle.cantidadPlaga <= 0) {
            return Result.failure(Exception("La cantidad debe ser mayor a 0"))
        }

        return try {
            repository.insertDetalleIncidencia(detalle)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Error al guardar detalle de incidencia: ${e.message}"))
        }
    }
}

