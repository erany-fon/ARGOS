package com.example.my_app_agroberries.ui.screens.inspeccion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun InspeccionScreen(
    idSurco: Int,
    idUsuario: Int,
    onRegistroGuardado: () -> Unit,
    viewModel: InspeccionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // cuando se guarda exitosamente regresa al surco
    LaunchedEffect(uiState.guardadoExitoso) {
        if (uiState.guardadoExitoso) onRegistroGuardado()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            // scroll importante para pantallas pequeñas de gama baja
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // ── Header ────────────────────────────────────
        Text(
            text = "Registrar Plaga",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Surco #$idSurco",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        HorizontalDivider()

        // ── Selector tipo de plaga ────────────────────
        Text(
            text = "Tipo de plaga *",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )

        if (uiState.tiposPlagas.isEmpty()) {
            Text(
                text = "No hay tipos de plaga registrados",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )
        } else {
            uiState.tiposPlagas.forEach { tipo ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (uiState.tipoPlagaSeleccionado?.idTipoPlaga == tipo.idTipoPlaga)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = uiState.tipoPlagaSeleccionado?.idTipoPlaga == tipo.idTipoPlaga,
                            onClick = { viewModel.onTipoPlagaSeleccionado(tipo) }
                        )
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text(
                                text = tipo.nombreTipoPlaga,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                            Text(
                                text = tipo.descripcion,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider()

        // ── Campo cantidad ────────────────────────────
        Text(
            text = "Cantidad encontrada *",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        OutlinedTextField(
            value = uiState.cantidad,
            onValueChange = viewModel::onCantidadChange,
            label = { Text("Número de plagas") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            isError = uiState.error != null && uiState.cantidad.isEmpty()
        )

        // ── Campo comentarios ─────────────────────────
        Text(
            text = "Comentarios",
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        OutlinedTextField(
            value = uiState.comentarios,
            onValueChange = viewModel::onComentariosChange,
            label = { Text("Observaciones (opcional)") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 5
        )

        // ── Error ─────────────────────────────────────
        if (uiState.error != null) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        // ── Botón guardar ─────────────────────────────
        Button(
            onClick = { viewModel.guardar(idSurco, idUsuario) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Guardar Registro",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // espacio al final para que el botón no quede pegado al teclado
        Spacer(modifier = Modifier.height(16.dp))
    }
}