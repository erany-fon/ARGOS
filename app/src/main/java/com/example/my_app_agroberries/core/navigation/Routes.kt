package com.example.my_app_agroberries.core.navigation

sealed class Routes(val route: String) {

    object Splash : Routes("splash")
    object Login : Routes("login")

    // ── Principal ─────────────────────────────────────
    object Perfil : Routes("perfil/{idUsuario}") {
        const val BASE = "perfil/{idUsuario}"
        fun createRoute(idUsuario: Int) = "perfil/$idUsuario"
    }

    data class Rancho(val idUsuario: Int) : Routes("rancho/{idUsuario}") {

        companion object {
            const val BASE = "rancho/{idUsuario}"
            fun createRoute(idUsuario: Int) = "rancho/$idUsuario"
        }
    }

    data class Tunel(val idRancho: Int) : Routes("tunel/{idRancho}") {
        companion object {
            const val BASE = "tunel/{idRancho}"
            fun createRoute(idRancho: Int) = "tunel/$idRancho"
        }
    }

    data class Surco(val idTunel: Int) : Routes("surco/{idTunel}") {
        companion object {
            const val BASE = "surco/{idTunel}"
            fun createRoute(idTunel: Int) = "surco/$idTunel"
        }
    }

    data class Inspeccion(val idSurco: Int, val idUsuario: Int) : Routes("inspeccion/{idSurco}/{idUsuario}") {
        companion object {
            const val BASE = "inspeccion/{idSurco}/{idUsuario}"
            fun createRoute(idSurco: Int, idUsuario: Int) = "inspeccion/$idSurco/$idUsuario"
        }
    }

    data class Sensado(val idTunel: Int) : Routes("sensado/{idTunel}") {
        companion object {
            const val BASE = "sensado/{idTunel}"
            fun createRoute(idTunel: Int) = "sensado/$idTunel"
        }
    }
}