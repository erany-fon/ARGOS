package com.example.my_app_agroberries.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.my_app_agroberries.R // Asegúrate de que este import coincida con tu paquete

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isReady by viewModel.isReady.collectAsState()

    // Animación infinita de "latido" (pulso tecnológico)
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    // Animación de aparición (fade-in) general
    var visible by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "fade_in"
    )

    LaunchedEffect(Unit) { visible = true }
    LaunchedEffect(isReady) { if (isReady) onNavigateToLogin() }

    // Degradado de fondo: Del Azul Marino (texto) al Cian (ojo)
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF1B262C), // Azul marino muy oscuro
            Color(0xFF00ACC1)  // Cian Argos
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alphaAnim)
        ) {
            // Reemplaza 'R.drawable.logo_argos' con el nombre real de tu imagen
            // Si aún no la tienes, esto mostrará un espacio en blanco temporalmente
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Argos",
                modifier = Modifier
                    .size(180.dp)
                    .scale(pulseScale) // Aplica el efecto de latido
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "ARGOS",
                color = Color.White,
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 6.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "AGRITECH INTELLIGENCE",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp
            )
        }
    }
}