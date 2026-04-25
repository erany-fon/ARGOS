package com.example.my_app_agroberries
import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import com.example.my_app_agroberries.data.local.AppDatabase
import com.example.my_app_agroberries.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
    @Inject
    lateinit var appDatabase: AppDatabase

    override fun onCreate() {
        super.onCreate()
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("MyApp", "Iniciando seeding de base de datos")
                seedDatabase()
            } catch (e: Exception) {
                Log.e("MyApp", "Error durante seeding", e)
            }
        }
    }

    private suspend fun seedDatabase() {
        try {
            // Verificar si ya existe dato de prueba
            val usuarioExiste = appDatabase.UsuarioDao().getUsuarioById(1)
            if (usuarioExiste != null) {
                Log.d("MyApp", "BD ya inicializada, skip seeding")
                return
            }

            Log.d("MyApp", "Insertando datos de prueba...")

            // Usuario
            appDatabase.UsuarioDao().insertUsuario(
                UsuarioEntity(
                    idUsuario = 1, idRol = 1, nombre = "admin", apellidoP = "Prueba",
                    apellidoM = "Usuario", usuario = "admin", email = "admin@agroberries.com",
                    passwordHash = "1234"
                )
            )

            // Roles
            appDatabase.RolesDao().insertAll(listOf(
                RolesEntity(1, "Supervisor"), RolesEntity(2, "Técnico"), RolesEntity(3, "Operario")
            ))

            // Ranchos
            appDatabase.RanchoDao().insertAll(listOf(
                RanchoEntity(1, "Rancho El Fresón"), RanchoEntity(2, "Rancho Las Berries")
            ))

            // Usuario-Rancho
            appDatabase.UsuarioRanchoDao().insertAll(listOf(
                UsuarioRanchoEntity(1, 1), UsuarioRanchoEntity(1, 2)
            ))

            // Túneles
            appDatabase.TunelDao().insertAll(listOf(
                TunelEntity(1, 1, 1), TunelEntity(2, 2, 1), TunelEntity(3, 1, 2)
            ))

            // Cultivos
            appDatabase.CultivoDao().insertAll(listOf(
                CultivoEntity(1, "Fresa", "Fruta roja"), CultivoEntity(2, "Frambuesa", "Fruta roja"),
                CultivoEntity(3, "Tomate", "Hortaliza"), CultivoEntity(4, "Manzana", "Fruta")
            ))

            // Surcos
            appDatabase.SurcoDao().insertAll(listOf(
                SurcoEntity(1, 1, 1, 1, "Fresa"), SurcoEntity(2, 1, 1, 2, "Fresa"),
                SurcoEntity(3, 1, 2, 3, "Frambuesa"), SurcoEntity(4, 2, 1, 1, "Fresa"),
                SurcoEntity(5, 2, 1, 2, "Fresa")
            ))

            // Plagas
            appDatabase.PlagaDao().insertAll(listOf(
                PlagaEntity(1, "Tetranychus urticae", "Araña roja"),
                PlagaEntity(2, "Aphis gossypii", "Pulgón"), PlagaEntity(3, "Frankliniella occidentalis", "Trips"),
                PlagaEntity(4, "Bemisia tabaci", "Mosca blanca")
            ))

            // Tipos Plaga
            appDatabase.TipoPlagaDao().insertAll(listOf(
                TipoPlagaEntity(1, 1, "Araña Roja", "Ácaro follaje"),
                TipoPlagaEntity(2, 2, "Pulgón", "Insecto savia"),
                TipoPlagaEntity(3, 3, "Trips", "Insecto flores"),
                TipoPlagaEntity(4, 4, "Mosca Blanca", "Insecto volador")
            ))

            // Incidencias
            appDatabase.incidenciaDao().insertAll(listOf(
                IncidenciaEntity(idIncidencia = 1, idSurco = 1, idUsuario = 1, idTipoPlaga = 1,
                    fecha = System.currentTimeMillis() - 86400000, nivelAlerta = 3, comentarios = "Grave", fotoUrl = "", sincronizado = false),
                IncidenciaEntity(idIncidencia = 2, idSurco = 2, idUsuario = 1, idTipoPlaga = 2,
                    fecha = System.currentTimeMillis() - 172800000, nivelAlerta = 2, comentarios = "Moderada", fotoUrl = "", sincronizado = false),
                IncidenciaEntity(idIncidencia = 3, idSurco = 3, idUsuario = 1, idTipoPlaga = 2,
                    fecha = System.currentTimeMillis() - 259200000, nivelAlerta = 1, comentarios = "Pequeña", fotoUrl = "", sincronizado = false)
            ))

            // Detalles Incidencias
            appDatabase.detalleIncidenciaDao().insertAll(listOf(
                DetalleIncidenciasEntity(idDetalleIncidencia = 1, idIncidencia = 1, idTipoPlaga = 1, idPlaga = 1, cantidadPlaga = 150),
                DetalleIncidenciasEntity(idDetalleIncidencia = 2, idIncidencia = 2, idTipoPlaga = 2, idPlaga = 2, cantidadPlaga = 200),
                DetalleIncidenciasEntity(idDetalleIncidencia = 3, idIncidencia = 3, idTipoPlaga = 3, idPlaga = 3, cantidadPlaga = 50)
            ))

            Log.d("MyApp", "Seeding completado exitosamente")
        } catch (e: Exception) {
            Log.e("MyApp", "Error en seedDatabase", e)
        }
    }
}