package com.example.my_app_agroberries.ui.screens.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SaludChart(
    data: List<Float> = listOf(95f, 92f, 88f, 94f, 80f, 75f, 90f),
    modifier: Modifier = Modifier
) {
    val maxSalud = 100f
    val lineColor = Color(0xFF2E7D32)
    val gradientColors = listOf(Color(0xFF4CAF50).copy(alpha = 0.4f), Color.Transparent)
    val guideColor = Color.LightGray.copy(alpha = 0.5f)

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxHeight().padding(end = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text("100%", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Text("50%", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Text("0%", fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, top = 8.dp, bottom = 8.dp)
        ) {
            val width = size.width
            val height = size.height
            val spacePerDay = if (data.size > 1) width / (data.size - 1) else width

            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            drawLine(guideColor, Offset(0f, 0f), Offset(width, 0f), pathEffect = pathEffect)
            drawLine(guideColor, Offset(0f, height / 2), Offset(width, height / 2), pathEffect = pathEffect)
            drawLine(guideColor, Offset(0f, height), Offset(width, height), strokeWidth = 2f)

            val path = Path()
            val fillPath = Path()
            var lastX = 0f
            var previousX = 0f
            var previousY = height - ((data.first() / maxSalud) * height)

            data.forEachIndexed { index, value ->
                val x = index * spacePerDay
                val y = height - ((value / maxSalud) * height)

                if (index == 0) {
                    path.moveTo(x, y)
                    fillPath.moveTo(x, height)
                    fillPath.lineTo(x, y)
                } else {
                    val controlPointX = (x + previousX) / 2f
                    path.cubicTo(controlPointX, previousY, controlPointX, y, x, y)
                    fillPath.cubicTo(controlPointX, previousY, controlPointX, y, x, y)
                }

                previousX = x
                previousY = y
                lastX = x
            }

            fillPath.lineTo(lastX, height)
            fillPath.close()

            drawPath(path = fillPath, brush = Brush.verticalGradient(colors = gradientColors, startY = 0f, endY = height))
            drawPath(path = path, color = lineColor, style = Stroke(width = 4.dp.toPx()))

            data.forEachIndexed { index, value ->
                val x = index * spacePerDay
                val y = height - ((value / maxSalud) * height)
                drawCircle(color = Color.White, radius = 6.dp.toPx(), center = Offset(x, y))
                drawCircle(color = lineColor, radius = 4.dp.toPx(), center = Offset(x, y))
            }
        }
    }
}

@Composable
fun InspeccionCalendar(
    diaActual: Int = 27,
    diasInspeccionados: List<Int> = (1..26).filter { it != 15 && it != 22 },
    modifier: Modifier = Modifier
) {
    val diasSemana = listOf("L", "M", "M", "J", "V", "S", "D")

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            diasSemana.forEach { dia ->
                Text(text = dia, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 12.sp)
            }
        }

        // EL ARREGLO ESTÁ AQUÍ: userScrollEnabled = false
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(2.dp),
            userScrollEnabled = false
        ) {
            items(30) { index ->
                val dia = index + 1
                val fueInspeccionado = diasInspeccionados.contains(dia)
                val esPasado = dia < diaActual
                val esHoy = dia == diaActual

                val bgColor = when {
                    fueInspeccionado -> Color(0xFFE8F5E9)
                    esPasado && !fueInspeccionado -> Color(0xFFFFEBEE)
                    else -> Color.Transparent
                }

                val contentColor = when {
                    fueInspeccionado -> Color(0xFF2E7D32)
                    esPasado && !fueInspeccionado -> Color(0xFFD32F2F)
                    else -> Color.LightGray
                }

                val todayModifier = if (esHoy) Modifier.border(2.dp, Color(0xFF2E7D32), RoundedCornerShape(12.dp)) else Modifier

                Box(
                    modifier = Modifier.padding(4.dp).aspectRatio(1f).then(todayModifier).background(bgColor, shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        fueInspeccionado -> Icon(Icons.Default.Check, contentDescription = null, tint = contentColor, modifier = Modifier.size(20.dp))
                        esPasado && !fueInspeccionado -> Icon(Icons.Default.Close, contentDescription = null, tint = contentColor, modifier = Modifier.size(16.dp))
                        else -> Text(text = dia.toString(), fontSize = 14.sp, fontWeight = if (esHoy) FontWeight.Bold else FontWeight.Normal, color = if (esHoy) Color(0xFF2E7D32) else contentColor)
                    }
                }
            }
        }
    }
}