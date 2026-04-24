package com.example.my_app_agroberries.di

import android.content.Context
import androidx.room.Room
import com.example.my_app_agroberries.data.local.AppDatabase
import com.example.my_app_agroberries.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "agroberries_db"
        ).build()
    }

    @Provides fun provideUsuarioDao(db: AppDatabase): UsuarioDao = db.UsuarioDao()
    @Provides fun provideRolesDao(db: AppDatabase): RolesDao = db.RolesDao()
    @Provides fun providePlagaDao(db: AppDatabase): PlagaDao = db.PlagaDao()
    @Provides fun provideRanchoDao(db: AppDatabase): RanchoDao = db.RanchoDao()
    @Provides fun provideTunelDao(db: AppDatabase): TunelDao = db.TunelDao()

    @Provides fun provideCultivoDao(db: AppDatabase): CultivoDao = db.CultivoDao()
    @Provides fun provideSurcoDao(db: AppDatabase): SurcoDao = db.SurcoDao()
    @Provides fun provideTipoPlagaDao(db: AppDatabase): TipoPlagaDao = db.TipoPlagaDao()
    @Provides fun provideIncidenciaDao(db: AppDatabase): IncidenciaDao = db.incidenciaDao()
    @Provides fun provideDetalleIncidenciaDao(db: AppDatabase): DetalleIncidenciaDao = db.detalleIncidenciaDao()
    @Provides fun provideUsuarioRanchoDao(db: AppDatabase): UsuarioRanchoDao = db.UsuarioRanchoDao()
}
