package com.example.my_app_agroberries.di

import com.example.my_app_agroberries.data.local.AppDatabase
import com.example.my_app_agroberries.data.local.entity.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseSeeder @Inject constructor(
    private val db: AppDatabase
) {
    fun seed() {
        CoroutineScope(Dispatchers.IO).launch {

            // solo inserta si la BD está vacía
            val usuarioExiste = db.UsuarioDao().getUsuarioById(1)
            if (usuarioExiste != null) return@launch

            // Usuario de prueba
            db.UsuarioDao().insertUsuario(
                UsuarioEntity(
                    idUsuario = 1,
                    nombre = "admin",
                    apellido = "Prueba",
                    email = "admin@agroberries.com",
                    contrasenaHash = "1234",  // en producción irá hasheado
                    rol = "supervisor"
                )
            )

            // Ranchos
            db.RanchoDao().insertAll(listOf(

                RanchoEntity(idRancho = 1, nombreRancho = "Rancho El Fresón"),
                RanchoEntity(idRancho = 2, nombreRancho = "Rancho Las Berries")
            ))

            //  Ligar usuario con ranchos
            db.UsuarioRanchoDao().insertAll(listOf(
                UsuarioRanchoEntity(idUsuario = 1, idRancho = 1),
                UsuarioRanchoEntity(idUsuario = 1, idRancho = 2)
            ))

            //  Túneles
            db.TunelDao().insertAll(listOf(
                TunelEntity(idTunel = 1, numeroTunel = 1, idRancho = 1),
                TunelEntity(idTunel = 2, numeroTunel = 2, idRancho = 1),
                TunelEntity(idTunel = 3, numeroTunel = 1, idRancho = 2)
            ))

            // Surcos
            db.SurcoDao().insertAll(listOf(
                SurcoEntity(idSurco = 1, numeroSurco = 1, tipoSurco = "Fresa", idTunel = 1),
                SurcoEntity(idSurco = 2, numeroSurco = 2, tipoSurco = "Fresa", idTunel = 1),
                SurcoEntity(idSurco = 3, numeroSurco = 3, tipoSurco = "Frambuesa", idTunel = 1),
                SurcoEntity(idSurco = 4, numeroSurco = 1, tipoSurco = "Fresa", idTunel = 2),
                SurcoEntity(idSurco = 5, numeroSurco = 2, tipoSurco = "Fresa", idTunel = 2)
            ))

            //  Tipos de plaga
            db.TipoPlagaDao().insertAll(listOf(
                TipoPlagaEntity(
                    idTipoPlaga = 1,
                    nombreTipoPlaga = "Araña Roja",
                    descripcion = "Ácaro que ataca el follaje"
                ),
                TipoPlagaEntity(
                    idTipoPlaga = 2,
                    nombreTipoPlaga = "Pulgón",
                    descripcion = "Insecto chupador de savia"
                ),
                TipoPlagaEntity(
                    idTipoPlaga = 3,
                    nombreTipoPlaga = "Trips",
                    descripcion = "Insecto pequeño que daña flores"
                ),
                TipoPlagaEntity(
                    idTipoPlaga = 4,
                    nombreTipoPlaga = "Mosca Blanca",
                    descripcion = "Insecto volador plaga común"
                )
            ))
        }
    }
}