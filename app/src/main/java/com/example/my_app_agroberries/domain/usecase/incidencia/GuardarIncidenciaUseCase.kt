package com.example.my_app_agroberries.domain.usecase.incidencia

import com.example.my_app_agroberries.domain.model.Incidencia
import com.example.my_app_agroberries.domain.repository.IncidenciaRepository
import javax.inject.Inject

class GuardarIncidenciaUseCase @Inject constructor(
    private val repository: IncidenciaRepository
) {
    suspend operator fun invoke(incidencia: Incidencia): Result<Int> {

        // validaciones del negocio
        if (incidencia.idSurco <= 0) {
            return Result.failure(Exception("Debes seleccionar un surco válido"))
        }
        if (incidencia.idTipoPlaga <= 0) {
            return Result.failure(Exception("Debes seleccionar un tipo de plaga"))
        }
        if (incidencia.nivelAlerta < 0 || incidencia.nivelAlerta > 5) {
            return Result.failure(Exception("Nivel de alerta debe estar entre 0 y 5"))
        }

        return try {
            // guarda localmente primero — offline first
            // sincronizado = false por defecto hasta que llegue al servidor
            val incidenciaAGuardar = incidencia.copy(
                fecha = System.currentTimeMillis(), // timestamp actual
                sincronizado = false                // pendiente de sync
            )
            repository.guardarIncidencia(incidenciaAGuardar)
            Result.success(incidenciaAGuardar.idIncidencia)
        } catch (e: Exception) {
            Result.failure(Exception("Error al guardar el registro: ${e.message}"))
        }
    }
}