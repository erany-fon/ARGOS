package com.example.my_app_agroberries
import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.example.my_app_agroberries.di.DatabaseSeeder
import javax.inject.Inject


@HiltAndroidApp
class MyApp : Application() {
    @Inject
    lateinit var seeder: DatabaseSeeder

    override fun onCreate() {
        super.onCreate()
        //inserta datos de prueba
        seeder.seed()
    }
}