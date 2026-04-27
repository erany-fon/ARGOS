package com.example.my_app_agroberries.ui.screens.inspeccion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InspeccionScreen(
    idSurco: Int,
    idUsuario: Int,
    onRegistroGuardado: () -> Unit,
    viewModel: InspeccionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Colores base de la marca ARGOS
    val argosNavy = Color(0xFF0A0A0A)
    val argosTeal = Color(0xFF00ACC1)
    val argosGreen = Color(0xFF689F38)
    val argosBackground = Color(0xFFF7F9F7)
    val argosAlert = Color(0xFFE65100) // Naranja de alerta

    // Efecto de navegación al guardar
    LaunchedEffect(uiState.guardadoExitoso) {
        if (uiState.guardadoExitoso) onRegistroGuardado()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = argosBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding() // ¡Vital! Evita que el teclado tape los inputs
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // ── Header Superior ────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(argosNavy, Color(0xFF1B262C))
                        )
                    )
            ) {
                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(argosAlert.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.BugReport,
                            contentDescription = "Plaga",
                            tint = argosAlert,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Reporte de Plaga",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Inspección en Surco #$idSurco",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Contenedor principal del formulario con padding
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                // ── Selector de Tipo de Plaga ────────────────────
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "1. ¿Qué plaga detectaste? *",
                        fontWeight = FontWeight.Bold,
                        color = argosNavy,
                        fontSize = 18.sp
                    )

                    if (uiState.tiposPlagas.isEmpty()) {
                        Text(
                            text = "Cargando tipos de plaga...",
                            color = argosNavy.copy(alpha = 0.6f),
                            fontSize = 14.sp
                        )
                    } else {
                        // Tarjetas de plagas en lugar de radio buttons
                        uiState.tiposPlagas.forEach { tipo ->
                            val isSelected = uiState.tipoPlagaSeleccionado?.idTipoPlaga == tipo.idTipoPlaga

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { viewModel.onTipoPlagaSeleccionado(tipo) },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) argosTeal.copy(alpha = 0.05f) else Color.White
                                ),
                                border = if (isSelected) BorderStroke(2.dp, argosTeal) else BorderStroke(1.dp, argosNavy.copy(alpha = 0.1f)),
                                elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 0.dp else 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { viewModel.onTipoPlagaSeleccionado(tipo) },
                                        colors = RadioButtonDefaults.colors(selectedColor = argosTeal)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = tipo.nombreTipoPlaga,
                                            fontWeight = FontWeight.Bold,
                                            color = argosNavy,
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = tipo.descripcion,
                                            fontSize = 13.sp,
                                            color = argosNavy.copy(alpha = 0.6f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(color = argosNavy.copy(alpha = 0.1f))

                // ── Configuración de Campos de Texto ──────────────────
                val textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = argosNavy,
                    unfocusedTextColor = argosNavy,
                    focusedBorderColor = argosTeal,
                    unfocusedBorderColor = argosNavy.copy(alpha = 0.3f),
                    focusedLabelColor = argosTeal,
                    unfocusedLabelColor = argosNavy.copy(alpha = 0.6f),
                    cursorColor = argosTeal,
                    errorBorderColor = argosAlert
                )

                // ── Campo Cantidad ────────────────────────────
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "2. Nivel de incidencia *",
                        fontWeight = FontWeight.Bold,
                        color = argosNavy,
                        fontSize = 18.sp
                    )
                    OutlinedTextField(
                        value = uiState.cantidad,
                        onValueChange = viewModel::onCantidadChange,
                        label = { Text("Cantidad aproximada (Ej. 10)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = textFieldColors,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        isError = uiState.error != null && uiState.cantidad.isEmpty()
                    )
                }

                // ── Campo Comentarios ─────────────────────────
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "3. Observaciones (Opcional)",
                        fontWeight = FontWeight.Bold,
                        color = argosNavy,
                        fontSize = 18.sp
                    )
                    OutlinedTextField(
                        value = uiState.comentarios,
                        onValueChange = viewModel::onComentariosChange,
                        label = { Text("Añade detalles sobre el estado del cultivo...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = textFieldColors,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        maxLines = 4
                    )
                }

                // ── Error ─────────────────────────────────────
                if (uiState.error != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = argosAlert.copy(alpha = 0.1f)),
                        border = BorderStroke(1.dp, argosAlert.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Error",
                                tint = argosAlert
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = uiState.error!!,
                                color = argosAlert,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ── Botón Guardar ─────────────────────────────
                Button(
                    onClick = { viewModel.guardar(idSurco, idUsuario) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !uiState.isLoading,
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(listOf(argosTeal, argosGreen)),
                                alpha = if (uiState.isLoading) 0.5f else 1f
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 3.dp
                            )
                        } else {
                            Text(
                                text = "Guardar Reporte",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                // Espacio extra al final para asegurar un buen scroll
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}