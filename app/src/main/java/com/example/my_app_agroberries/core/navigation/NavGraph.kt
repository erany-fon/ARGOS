package com.example.my_app_agroberries.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.my_app_agroberries.ui.screens.splash.SplashScreen
import com.example.my_app_agroberries.ui.screens.login.LoginScreen
import com.example.my_app_agroberries.ui.screens.perfil.PerfilScreen
import com.example.my_app_agroberries.ui.screens.rancho.RanchoScreen
import com.example.my_app_agroberries.ui.screens.tunel.TunelScreen
import com.example.my_app_agroberries.ui.screens.surco.SurcoScreen
import com.example.my_app_agroberries.ui.screens.inspeccion.InspeccionScreen
import com.example.my_app_agroberries.ui.screens.inspeccion.SensadoScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route  // ← primera pantalla
    ) {

        // ── Splash ────────────────────────────────────
        composable(route = Routes.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.Login.route) {
                        // elimina Splash del back stack
                        // el usuario no puede regresar al splash
                        popUpTo(Routes.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Login ─────────────────────────────────────
        composable(route = Routes.Login.route) {
            LoginScreen(
                onLoginSuccess = { idUsuario ->
                    navController.navigate(Routes.Rancho.createRoute(idUsuario)) {
                        // elimina Login del back stack
                        // el usuario no puede regresar al login con atrás
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Perfil ────────────────────────────────────
        composable(
            route = Routes.Perfil.BASE,
            arguments = listOf(
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
            PerfilScreen(
                idUsuario = idUsuario,
                onNavigateToRancho = { id ->
                    navController.navigate(Routes.Rancho.createRoute(id))
                }
            )
        }

        // ── Rancho ────────────────────────────────────
        composable(
            route = Routes.Rancho.BASE,
            arguments = listOf(
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
            RanchoScreen(
                idUsuario = idUsuario,
                onNavigateToTunel = { idRancho ->
                    navController.navigate(Routes.Tunel.createRoute(idRancho))
                }
            )
        }

        // ── Túnel ─────────────────────────────────────
        composable(
            route = Routes.Tunel.BASE,
            arguments = listOf(
                navArgument("idRancho") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idRancho = backStackEntry.arguments?.getInt("idRancho") ?: 0
            TunelScreen(
                idRancho = idRancho,
                onNavigateToSurco = { idTunel ->
                    navController.navigate(Routes.Surco.createRoute(idTunel))
                },
                onNavigateToSensado = { idTunel ->
                    navController.navigate(Routes.Sensado.createRoute(idTunel))
                }
            )
        }

        // ── Surco ─────────────────────────────────────
        composable(
            route = Routes.Surco.BASE,
            arguments = listOf(
                navArgument("idTunel") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idTunel = backStackEntry.arguments?.getInt("idTunel") ?: 0
            // recupera idUsuario del back stack desde RanchoScreen
            val idUsuario = navController.previousBackStackEntry
                ?.arguments?.getInt("idUsuario") ?: 0
            SurcoScreen(
                idTunel = idTunel,
                onNavigateToInspeccion = { idSurco ->
                    navController.navigate(Routes.Inspeccion.createRoute(idSurco, idUsuario))
                }
            )
        }

        // ── Inspección ────────────────────────────────
        composable(
            route = Routes.Inspeccion.BASE,
            arguments = listOf(
                navArgument("idSurco") { type = NavType.IntType },
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idSurco = backStackEntry.arguments?.getInt("idSurco") ?: 0
            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
            InspeccionScreen(
                idSurco = idSurco,
                idUsuario = idUsuario,
                onRegistroGuardado = {
                    navController.popBackStack()
                }
            )
        }

        // ── Sensado (Nueva) ───────────────────────────
        composable(
            route = Routes.Sensado.BASE,
            arguments = listOf(
                navArgument("idTunel") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idTunel = backStackEntry.arguments?.getInt("idTunel") ?: 0
            SensadoScreen(
                idTunel = idTunel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}