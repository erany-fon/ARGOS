package com.example.my_app_agroberries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.my_app_agroberries.core.navigation.NavGraph
import com.example.my_app_agroberries.ui.theme.My_App_AgroBerriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My_App_AgroBerriesTheme {
                // Surface actúa como el fondo base de la app, tomando el color del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Inicializamos el controlador de navegación
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }
}