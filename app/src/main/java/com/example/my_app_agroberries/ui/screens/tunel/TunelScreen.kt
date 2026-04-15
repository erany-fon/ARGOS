package com.example.my_app_agroberries.ui.screens.tunel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.my_app_agroberries.domain.model.Tunel

@Composable
fun TunelScreen(
    idRancho: Int,
    onNavigateToSurco: (Int) -> Unit,
    viewModel: TunelViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(idRancho) {
        viewModel.cargarTuneles(idRancho)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Selecciona un Túnel",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${uiState.tuneles.size} túneles disponibles",
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.tuneles.isEmpty() -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("No hay túneles en este rancho")
                }
            }
            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = uiState.tuneles, key = { it.idTunel }) { tunel ->
                        TunelItem(
                            tunel = tunel,
                            onClick = { onNavigateToSurco(tunel.idTunel) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TunelItem(tunel: Tunel, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Túnel ${tunel.numeroTunel}",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Icon(Icons.Default.KeyboardArrowRight, null,
                tint = MaterialTheme.colorScheme.primary)
        }
    }
}