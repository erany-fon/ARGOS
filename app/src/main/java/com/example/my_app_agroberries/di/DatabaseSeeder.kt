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
                    idRol = 1,
                    nombre = "admin",
                    apellidoP = "Prueba",
                    apellidoM = "Usuario",
                    usuario = "admin",
                    email = "admin@agroberries.com",
                    passwordHash = "1234"  // en producción irá hasheado
                )
            )

            // Roles
            db.RolesDao().insertAll(listOf(
                RolesEntity(idRol = 1, nombreRol = "Supervisor"),
                RolesEntity(idRol = 2, nombreRol = "Técnico"),
                RolesEntity(idRol = 3, nombreRol = "Operario")
            ))

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

            //cultivos
            db.CultivoDao().insertAll(listOf(
                CultivoEntity(idCultivo = 1, nombreCultivo = "Fresa", descripcion = "Fruta de la pasión"),
                CultivoEntity(idCultivo = 2, nombreCultivo = "Frambuesa", descripcion = "Fruta de la pasión"),
                CultivoEntity(idCultivo = 3, nombreCultivo = "Tomate", descripcion = "Fruta de la pasión"),
                CultivoEntity(idCultivo = 4, nombreCultivo = "Manzana", descripcion = "Fruta de la pasión")
            ))

            // Surcos
            db.SurcoDao().insertAll(listOf(
                SurcoEntity(idSurco = 1, numeroSurco = 1, cultivo = "Fresa", idTunel = 1, idCultivo = 1),
                SurcoEntity(idSurco = 2, numeroSurco = 2, cultivo = "Fresa", idTunel = 1, idCultivo = 1),
                SurcoEntity(idSurco = 3, numeroSurco = 3, cultivo = "Frambuesa", idTunel = 1, idCultivo = 2),
                SurcoEntity(idSurco = 4, numeroSurco = 1, cultivo = "Fresa", idTunel = 2, idCultivo = 1),
                SurcoEntity(idSurco = 5, numeroSurco = 2, cultivo = "Fresa", idTunel = 2, idCultivo = 1)
            ))

            // Plagas
            db.PlagaDao().insertAll(listOf(
                PlagaEntity(idPlaga = 1, nombrePlaga = "Tetranychus urticae", descripcion = "Araña roja común"),
                PlagaEntity(idPlaga = 2, nombrePlaga = "Aphis gossypii", descripcion = "Pulgón del algodón"),
                PlagaEntity(idPlaga = 3, nombrePlaga = "Frankliniella occidentalis", descripcion = "Trips de las flores"),
                PlagaEntity(idPlaga = 4, nombrePlaga = "Bemisia tabaci", descripcion = "Mosca blanca del tabaco")
            ))
            //  Tipos de plaga
            db.TipoPlagaDao().insertAll(listOf(
                TipoPlagaEntity(
                    idTipoPlaga = 1,
                    idPlaga = 1,
                    nombreTipoPlaga = "Araña Roja",
                    descripcion = "Ácaro que ataca el follaje"
                ),
                TipoPlagaEntity(
                    idTipoPlaga = 2,
                    idPlaga = 2,
                    nombreTipoPlaga = "Pulgón",
                    descripcion = "Insecto chupador de savia"
                ),
                TipoPlagaEntity(
                    idTipoPlaga = 3,
                    idPlaga = 3,
                    nombreTipoPlaga = "Trips",
                    descripcion = "Insecto pequeño que daña flores"
                ),
                TipoPlagaEntity(
                    idTipoPlaga = 4,
                    idPlaga = 4,
                    nombreTipoPlaga = "Mosca Blanca",
                    descripcion = "Insecto volador plaga común"
                )
            ))

            //incidencias
            db.incidenciaDao().insertAll(listOf(
                IncidenciaEntity(
                    idIncidencia = 1,
                    idSurco = 1,
                    idTipoPlaga = 1,
                    idUsuario = 1,
                    fecha = System.currentTimeMillis() - 86400000,
                    nivelAlerta = 3,
                    comentarios = "Incidencia grave",
                    fotoUrl = "https://picsum.photos/200/300",
                    sincronizado = false
                ),
                IncidenciaEntity(
                    idIncidencia = 2,
                    idSurco = 2,
                    idTipoPlaga = 2,
                    idUsuario = 1,
                    fecha = System.currentTimeMillis() - 172800000,
                    nivelAlerta = 2,
                    comentarios = "Incidencia moderada",
                    fotoUrl = "https://picsum.photos/200/300",
                    sincronizado = false
                ),
                IncidenciaEntity(
                    idIncidencia = 3,
                    idSurco = 3,
                    idTipoPlaga = 2,
                    idUsuario = 1,
                    fecha = System.currentTimeMillis() - 259200000,
                    nivelAlerta = 1,
                    comentarios = "Incidencia pequeña",
                    fotoUrl = "https://picsum.photos/200/300",
                    sincronizado = false
                )
            ))


            // Detalles de incidencias
            db.detalleIncidenciaDao().insertAll(listOf(
                DetalleIncidenciasEntity(idDetalleIncidencia = 1, idIncidencia = 1, idTipoPlaga = 1, idPlaga = 1, cantidadPlaga = 150),
                DetalleIncidenciasEntity(idDetalleIncidencia = 2, idIncidencia = 2, idTipoPlaga = 2, idPlaga = 2, cantidadPlaga = 200),
                DetalleIncidenciasEntity(idDetalleIncidencia = 3, idIncidencia = 3, idTipoPlaga = 3, idPlaga = 3, cantidadPlaga = 50)
            ))
        }
    }
}