package com.example.my_app_agroberries.domain.usecase.incidencia

import com.example.my_app_agroberries.domain.model.Incidencia
import com.example.my_app_agroberries.domain.repository.IncidenciaRepository
import kotlinx.coroutines.flow.Flow

class ObtenerIncidenciasUsuarioUseCase(
    private val repository: IncidenciaRepository
) {
    operator fun invoke(idUsuario: Int): Flow<List<Incidencia>> {
        return repository.getIncidenciasByUsuario(idUsuario)
    }
}

