package com.example.my_app_agroberries.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.my_app_agroberries.data.local.dao.*
import com.example.my_app_agroberries.data.local.entity.*
import android.content.Context
import androidx.room.Room

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
    version = 2,
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

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "agroberries.db"
            )
                .fallbackToDestructiveMigration()  // ← AGREGAR ESTA LÍNEA
                .build()
        }
    }
}