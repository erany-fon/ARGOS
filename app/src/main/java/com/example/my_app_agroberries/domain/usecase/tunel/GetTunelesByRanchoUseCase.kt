package com.example.my_app_agroberries.domain.usecase.tunel

import com.example.my_app_agroberries.domain.model.Tunel
import com.example.my_app_agroberries.domain.repository.TunelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTunelesByRanchoUseCase @Inject constructor(
    private val repository: TunelRepository
) {
    operator fun invoke(idRancho: Int): Flow<List<Tunel>> {

        // validación — si el id no es válido regresa vacío
        if (idRancho <= 0) return kotlinx.coroutines.flow.flow { emit(emptyList()) }

        return repository.getTunelesByRancho(idRancho)
            .map { tuneles ->
                // ordena por número de tunel ascendente
                // así en la UI aparece Tunel 1, 2, 3...
                tuneles.sortedBy { it.numeroTunel }
            }
            .catch { emit(emptyList()) }
    }
}