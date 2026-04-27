package com.example.my_app_agroberries.ui.screens.surco

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Eco // Ícono de hoja/cultivo
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
import com.example.my_app_agroberries.domain.model.Surco

@Composable
fun SurcoScreen(
    idTunel: Int,
    onNavigateToInspeccion: (Int) -> Unit,
    viewModel: SurcoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Colores base de la marca
    val argosNavy = Color(0xFF0A0A0A)
    val argosTeal = Color(0xFF00ACC1)
    val argosGreen = Color(0xFF689F38)
    val argosBackground = Color(0xFFF7F9F7)

    LaunchedEffect(idTunel) {
        viewModel.cargarSurco(idTunel)
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
                            colors = listOf(argosNavy, argosGreen) // Usamos verde aquí para diferenciar niveles
                        )
                    )
            ) {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp)) {
                    Text(
                        text = "Selecciona un Surco",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${uiState.surcos.size} líneas de cultivo",
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
                        CircularProgressIndicator(color = argosGreen)
                    }
                }
                uiState.surcos.isEmpty() -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = argosNavy.copy(alpha = 0.2f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No hay surcos registrados",
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
                        items(items = uiState.surcos, key = { it.idSurco }) { surco ->
                            SurcoItem(
                                surco = surco,
                                argosNavy = argosNavy,
                                argosGreen = argosGreen,
                                onClick = { onNavigateToInspeccion(surco.idSurco) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SurcoItem(
    surco: Surco,
    argosNavy: Color,
    argosGreen: Color,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Ícono circular para el surco
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(argosGreen.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        tint = argosGreen,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Surco ${surco.numeroSurco}",
                        color = argosNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        // NOTA: Cambié surco.numeroSurco por surco.nombreCultivo. Si te marca error,
                        // asegúrate de que tu data class Surco tenga esta propiedad.
                        text = "Cultivo: ${surco.numeroSurco}",                        fontSize = 13.sp,
                        color = argosNavy.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFFF0F0F0), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Inspeccionar surco",
                    tint = argosNavy.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}