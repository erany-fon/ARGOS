package com.example.my_app_agroberries.domain.usecase.surco

import com.example.my_app_agroberries.domain.model.Surco
import com.example.my_app_agroberries.domain.repository.SurcoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSurcosByTunelUseCase @Inject constructor(
    private val repository: SurcoRepository
) {
    operator fun invoke(idTunel: Int): Flow<List<Surco>> {

        if (idTunel <= 0) return kotlinx.coroutines.flow.flow { emit(emptyList()) }

        return repository.getSurcosByTunel(idTunel)
            .map { surcos ->
                // ordena por número de surco ascendente
                // Surco 1, 2, 3...
                surcos.sortedBy { it.numeroSurco }
            }
            .catch { emit(emptyList()) }
    }
}