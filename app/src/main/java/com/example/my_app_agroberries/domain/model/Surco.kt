package com.example.my_app_agroberries.domain.model


data class Surco(
    val id_surco: Int,
    val numero_surco: Int,
    val tipo_cultivo: String,
    val id_tunel: Tunel
)