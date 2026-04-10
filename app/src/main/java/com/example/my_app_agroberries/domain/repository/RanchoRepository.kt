package com.example.my_app_agroberries.domain.repository

import com.example.my_app_agroberries.domain.model.Rancho
import kotlinx.coroutines.flow.Flow

interface RanchoRepository {
    //trae todos los ranchos asigandos a un usuario
    //flow se actualiza automaticamente si cambia la base Da
    fun getRanchosByUsuario(idUsuario: Int): Flow<List<Rancho>>

    //gurda los ranchos que llegaron al server
    suspend fun guardarRanchos(ranchos: List<Rancho>)

}