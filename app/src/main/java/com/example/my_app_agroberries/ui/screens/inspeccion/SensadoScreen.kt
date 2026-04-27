package com.example.my_app_agroberries.ui.screens.inspeccion

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BluetoothConnected
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SensadoScreen(
    idTunel: Int,
    onNavigateBack: () -> Unit
) {
    // Colores ARGOS
    val argosNavy = Color(0xFF0A0A0A)
    val argosTeal = Color(0xFF00ACC1)
    val argosGreen = Color(0xFF689F38)
    val argosBackground = Color(0xFFF7F9F7)

    // Variables de estado simuladas (Aquí conectarás tu lógica Bluetooth luego)
    // Usamos remember para simular que los datos cambian
    var temperatura by remember { mutableFloatStateOf(24.5f) }
    var humedad by remember { mutableFloatStateOf(65.0f) }
    var isConnected by remember { mutableStateOf(true) } // Cambia a false para ver el estado desconectado

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = argosBackground
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ── Header Dashboard ────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(listOf(argosNavy, argosTeal)))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Monitoreo IoT",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isConnected) Icons.Default.BluetoothConnected else Icons.Default.Bluetooth,
                            contentDescription = null,
                            tint = if (isConnected) argosGreen else Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isConnected) "Conectado a Arduino R4" else "Buscando sensor DHT11...",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // ── Relojes (Gauges) ────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {

                // Reloj de Temperatura (Máximo 50°C)
                ArgosGaugeCard(
                    title = "Temperatura Ambiental",
                    value = temperatura,
                    maxValue = 50f,
                    unit = "°C",
                    gaugeColor = Color(0xFFFF7043), // Naranja para calor
                    argosNavy = argosNavy
                )

                // Reloj de Humedad (Máximo 100%)
                ArgosGaugeCard(
                    title = "Humedad Relativa",
                    value = humedad,
                    maxValue = 100f,
                    unit = "%",
                    gaugeColor = argosTeal, // Cian para humedad/agua
                    argosNavy = argosNavy
                )
            }
        }
    }
}

// ── Componente Personalizado: El "Reloj" (Gauge) ──
@Composable
fun ArgosGaugeCard(
    title: String,
    value: Float,
    maxValue: Float,
    unit: String,
    gaugeColor: Color,
    argosNavy: Color
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = argosNavy
            )

            Spacer(modifier = Modifier.height(24.dp))

            // DIBUJO DEL RELOJ EN CANVAS
            Box(
                modifier = Modifier.size(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 16.dp.toPx()
                    val arcSize = size.width - strokeWidth

                    // 1. Dibuja el fondo gris del reloj
                    drawArc(
                        color = Color(0xFFE0E0E0),
                        startAngle = 135f, // Empieza abajo a la izquierda
                        sweepAngle = 270f, // Da casi toda la vuelta
                        useCenter = false,
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = Size(arcSize, arcSize),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // 2. Dibuja el valor actual
                    val percentage = (value / maxValue).coerceIn(0f, 1f)
                    val sweepProgress = 270f * percentage

                    drawArc(
                        color = gaugeColor,
                        startAngle = 135f,
                        sweepAngle = sweepProgress,
                        useCenter = false,
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = Size(arcSize, arcSize),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }

                // Texto en el centro del reloj
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format("%.1f", value), // Un decimal
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = argosNavy
                    )
                    Text(
                        text = unit,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = argosNavy.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}