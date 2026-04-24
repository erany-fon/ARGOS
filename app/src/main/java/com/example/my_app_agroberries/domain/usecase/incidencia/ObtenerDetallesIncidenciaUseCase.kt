package com.example.my_app_agroberries.domain.usecase.incidencia

import com.example.my_app_agroberries.domain.model.DetalleIncidencia
import com.example.my_app_agroberries.domain.repository.DetalleIncidenciaRepository
import kotlinx.coroutines.flow.Flow

class ObtenerDetallesIncidenciaUseCase(
    private val repository: DetalleIncidenciaRepository
) {
    operator fun invoke(idIncidencia: Int): Flow<List<DetalleIncidencia>> {
        return repository.getDetallesByIncidencia(idIncidencia)
    }
}

