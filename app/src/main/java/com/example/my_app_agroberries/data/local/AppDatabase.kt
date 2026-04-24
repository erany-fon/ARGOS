package com.example.my_app_agroberries.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.my_app_agroberries.data.local.dao.*
import com.example.my_app_agroberries.data.local.entity.*

@Database(
    entities = [
        UsuarioEntity::class,
        RolesEntity::class,
        PlagaEntity::class,
        RanchoEntity::class,
        UsuarioRanchoEntity::class,
        TunelEntity::class,
        CultivoEntity::class,
        SurcoEntity::class,
        TipoPlagaEntity::class,
        IncidenciaEntity::class,
        DetalleIncidenciasEntity::class
    ],
    version = 1,
    exportSchema = false   //en producción tenemos que cambiarlo a true
)

    abstract class AppDatabase : RoomDatabase() {
        abstract fun UsuarioDao(): UsuarioDao
        abstract fun RolesDao(): RolesDao
        abstract fun PlagaDao(): PlagaDao
        abstract fun RanchoDao(): RanchoDao
        abstract fun UsuarioRanchoDao(): UsuarioRanchoDao
        abstract fun TunelDao(): TunelDao

        abstract fun CultivoDao(): CultivoDao
        abstract fun SurcoDao(): SurcoDao
        abstract fun TipoPlagaDao(): TipoPlagaDao
        abstract fun incidenciaDao(): IncidenciaDao
        abstract fun detalleIncidenciaDao(): DetalleIncidenciaDao
    }