package com.example.my_app_agroberries.domain.usecase.rancho

import com.example.my_app_agroberries.domain.model.Rancho
import com.example.my_app_agroberries.domain.repository.RanchoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRanchosByUsuarioUseCase @Inject constructor(
    private val repository: RanchoRepository
) {
    // regresa Flow porque la lista se actualiza en tiempo real
    operator fun invoke(idUsuario: Int): Flow<List<Rancho>> {
        return repository.getRanchosByUsuario(idUsuario)
            .map { ranchos ->
                // ordena por nombre para consistencia en UI
                ranchos.sortedBy { it.nombreRancho }
            }
            .catch { e ->
                // si hay error en el Flow, emite lista vacía
                // así la UI no se rompe el flujo
                emit(emptyList())
            }
    }
}