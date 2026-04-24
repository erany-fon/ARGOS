package com.example.my_app_agroberries.ui.screens.surco

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

import com.example.my_app_agroberries.domain.model.Surco

@Composable
fun SurcoScreen(
    idTunel: Int,
    onNavigateToInspeccion: (Int) -> Unit,  // pasa idSurco seleccionado
    viewModel: SurcoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // carga surcos cuando entra a la pantalla
    LaunchedEffect(idTunel) {
        viewModel.cargarSurco(idTunel)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // ── Header ────────────────────────────────────
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Selecciona un Surco",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${uiState.surcos.size} surcos disponibles",
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        // ── Contenido ─────────────────────────────────
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.surcos.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay surcos en este túnel",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = uiState.surcos,
                        key = { it.idSurco }  // evita recomposiciones innecesarias
                    ) { surco ->
                        SurcoItem(
                            surco = surco,
                            onClick = { onNavigateToInspeccion(surco.idSurco) }
                        )
                    }
                }
            }
        }
    }
}

// ── Componente individual de surco ─────────────────
@Composable
private fun SurcoItem(
    surco: Surco,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Surco ${surco.numeroSurco}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Text(
                    text = "Cultivo: ${surco.numeroSurco}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}