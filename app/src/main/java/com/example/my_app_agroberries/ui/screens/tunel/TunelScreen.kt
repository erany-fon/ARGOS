package com.example.my_app_agroberries.ui.screens.tunel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ViewColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.my_app_agroberries.domain.model.Tunel

@Composable
fun TunelScreen(
    idRancho: Int,
    onNavigateToSurco: (Int) -> Unit,
    onNavigateToSensado: (Int) -> Unit, // <-- Añadido: Navegación a pantalla de IoT
    viewModel: TunelViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Colores base de la marca ARGOS
    val argosNavy = Color(0xFF0A0A0A)
    val argosTeal = Color(0xFF00ACC1)
    val argosBackground = Color(0xFFF7F9F7)

    LaunchedEffect(idRancho) {
        viewModel.cargarTuneles(idRancho)
    }

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
                            colors = listOf(argosNavy, argosTeal)
                        )
                    )
            ) {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {
                    Text(
                        text = "Selecciona un Túnel",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${uiState.tuneles.size} estructuras disponibles",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // ── Contenido Principal ─────────────────────────────────
            when {
                uiState.isLoading -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        CircularProgressIndicator(color = argosTeal)
                    }
                }
                uiState.tuneles.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.ViewColumn,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = argosNavy.copy(alpha = 0.2f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No hay túneles en esta área",
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
                        verticalArrangement = Arrangement.spacedBy(16.dp) // Aumentamos un poco el espacio entre tarjetas
                    ) {
                        items(items = uiState.tuneles, key = { it.idTunel }) { tunel ->
                            TunelItem(
                                tunel = tunel,
                                argosNavy = argosNavy,
                                argosTeal = argosTeal,
                                onNavigateToSurco = { onNavigateToSurco(tunel.idTunel) },
                                onNavigateToSensado = { onNavigateToSensado(tunel.idTunel) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TunelItem(
    tunel: Tunel,
    argosNavy: Color,
    argosTeal: Color,
    onNavigateToSurco: () -> Unit,
    onNavigateToSensado: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(), // Se quitó el .clickable general
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // ── Cabecera de la Tarjeta (Info del Túnel) ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(argosTeal.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ViewColumn,
                        contentDescription = null,
                        tint = argosTeal,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Túnel ${tunel.numeroTunel}",
                    color = argosNavy,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            HorizontalDivider(color = argosNavy.copy(alpha = 0.05f))

            // ── Botones de Acción (Mitad y Mitad) ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón 1: Inspección Manual
                TextButton(
                    onClick = onNavigateToSurco,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Visual",
                            tint = Color(0xFF689F38) // Verde Argos
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Registro Visual",
                            color = argosNavy,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Línea separadora vertical
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(40.dp)
                        .background(argosNavy.copy(alpha = 0.1f))
                )

                // Botón 2: Sensores IoT (Arduino)
                TextButton(
                    onClick = onNavigateToSensado,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Bluetooth,
                            contentDescription = "Sensores",
                            tint = argosTeal // Cian Argos
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Sensores IoT",
                            color = argosNavy,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}