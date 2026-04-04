package com.example.my_app_agroberries.domain.model

data class Tunel(
    val id_tunel: Int,
    val numero_tunel: String,
    val id_rancho: Rancho
)