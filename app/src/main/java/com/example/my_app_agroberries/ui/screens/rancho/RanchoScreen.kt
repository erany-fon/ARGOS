package com.example.my_app_agroberries.ui.screens.rancho

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn // O usa Icons.Outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.my_app_agroberries.domain.model.Rancho

@Composable
fun RanchoScreen(
    idUsuario: Int,
    onNavigateToTunel: (Int) -> Unit,
    viewModel: RanchoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Colores base de la marca
    val argosNavy = Color(0xFF0A0A0A)
    val argosTeal = Color(0xFF00ACC1)
    val argosGreen = Color(0xFF689F38)
    val argosBackground = Color(0xFFF7F9F7)

    LaunchedEffect(idUsuario) {
        viewModel.cargarRanchos(idUsuario)
    }

    // Usamos Surface para aplicar el color de fondo general a toda la pantalla
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = argosBackground
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ── Header con Gradiente ARGOS ────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(argosNavy, argosTeal) // De oscuro a brillante
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = "Mis Parcelas", // "Ranchos" suena bien, pero "Parcelas" o "Campos" puede ser más específico, ¡elige el que prefieras!
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tienes ${uiState.ranchos.size} áreas asignadas",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // ── Contenido Principal ─────────────────────────────────
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = argosTeal)
                    }
                }

                uiState.ranchos.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = argosNavy.copy(alpha = 0.2f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No tienes áreas asignadas",
                                color = argosNavy.copy(alpha = 0.6f),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.ranchos,
                            key = { it.idRancho }
                        ) { rancho ->
                            RanchoItem(
                                rancho = rancho,
                                argosNavy = argosNavy,
                                argosTeal = argosTeal,
                                onClick = { onNavigateToTunel(rancho.idRancho) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RanchoItem(
    rancho: Rancho,
    argosNavy: Color,
    argosTeal: Color,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)) // Asegura que el ripple effect no se salga de los bordes curvos
            .clickable { onClick() },
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Ícono circular para darle más peso visual
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(argosTeal.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn, // Puedes cambiarlo por un icono de tractor o planta si lo agregas luego
                        contentDescription = null,
                        tint = argosTeal,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = rancho.nombreRancho,
                        color = argosNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "ID: ${rancho.idRancho}",
                        fontSize = 13.sp,
                        color = argosNavy.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Indicador de acción sutil
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFFF0F0F0), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Entrar al rancho",
                    tint = argosNavy.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}