package com.example.my_app_agroberries.di

import com.example.my_app_agroberries.data.repository.*
import com.example.my_app_agroberries.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // @Binds  liga la interfaz con su implementación
    @Binds @Singleton
    abstract fun bindUsuarioRepository(
        impl: UsuarioRepositoryImpl
    ): UsuarioRepository

    @Binds @Singleton
    abstract fun bindRanchoRepository(
        impl: RanchoRepositoryImpl
    ): RanchoRepository

    @Binds @Singleton
    abstract fun bindTunelRepository(
        impl: TunelRepositoryImpl
    ): TunelRepository

    @Binds @Singleton
    abstract fun bindSurcoRepository(
        impl: SurcoRepositoryImpl
    ): SurcoRepository

    @Binds @Singleton
    abstract fun bindTipoPlagaRepository(
        impl: TipoPlagaRepositoryImpl
    ): TipoPlagaRepository

    @Binds @Singleton
    abstract fun bindIncidenciaRepository(
        impl: IncidenciaRepositoryImpl
    ): IncidenciaRepository
}