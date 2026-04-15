package com.example.my_app_agroberries.domain.usecase.incidencia

import com.example.my_app_agroberries.domain.model.IncidenciaPlaga
import com.example.my_app_agroberries.domain.repository.IncidenciaRepository
import javax.inject.Inject

class GuardarIncidenciaUseCase @Inject constructor(
    private val repository: IncidenciaRepository
) {
    suspend operator fun invoke(incidencia: IncidenciaPlaga): Result<Unit> {

        // validaciones del negocio
        if (incidencia.cantidadPlaga < 0) {
            return Result.failure(Exception("La cantidad de plaga no puede ser negativa"))
        }
        if (incidencia.cantidadPlaga == 0) {
            return Result.failure(Exception("Debes registrar al menos 1 plaga"))
        }
        if (incidencia.idSurco <= 0) {
            return Result.failure(Exception("Debes seleccionar un surco válido"))
        }
        if (incidencia.idTipoPlaga <= 0) {
            return Result.failure(Exception("Debes seleccionar un tipo de plaga"))
        }

        return try {
            // guarda localmente primero — offline first
            // sincronizado = false por defecto hasta que llegue al servidor
            repository.guardarIncidencia(
                incidencia.copy(
                    fecha = System.currentTimeMillis(), // timestamp actual
                    sincronizado = false                // pendiente de sync
                )
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Error al guardar el registro: ${e.message}"))
        }
    }
}