package com.example.my_app_agroberries.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToParcelas: () -> Unit,
    onNavigateToTuneles: () -> Unit,
    onNavigateToChat: () -> Unit, // <-- ¡NUEVA RUTA!
    viewModel: DashboardViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showNotificacionesMenu by remember { mutableStateOf(false) }

    val ArgosGreen = Color(0xFF1B5E20)
    val ArgosLightGreen = Color(0xFF4CAF50)
    val SurfaceWhite = Color(0xFFFFFFFF)
    val BackgroundWhite = Color(0xFFFDFDFD)
    val TextDark = Color(0xFF222222)

    val notificacionesRecientes = listOf(
        "💧 Túnel 1: Pérdida de humedad detectada.",
        "⚠️ Atención: No has hecho tu revisión diaria.",
        "🐛 Plagas: Revisión pendiente en Parcela 3.",
        "✅ Clima: Temperatura óptima en todos los sectores."
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = SurfaceWhite) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(180.dp)
                        .background(Brush.linearGradient(colors = listOf(ArgosGreen, ArgosLightGreen)))
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Icon(Icons.Default.Spa, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("ARGOS", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = Color.White)
                        Text("Ingeniería Agrícola", style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.8f))
                    }
                }

                Spacer(Modifier.height(16.dp))

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Dashboard Inicio", fontWeight = FontWeight.SemiBold) },
                    selected = true,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = ArgosLightGreen.copy(alpha = 0.15f), selectedTextColor = ArgosGreen, selectedIconColor = ArgosGreen
                    ),
                    onClick = { scope.launch { drawerState.close() } },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Map, contentDescription = null) },
                    label = { Text("Mis Parcelas") },
                    selected = false,
                    onClick = { onNavigateToParcelas() },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Sensors, contentDescription = null) },
                    label = { Text("Túneles IoT") },
                    selected = false,
                    onClick = { onNavigateToTuneles() },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
                // --- BOTÓN DEL CHATBOT ---
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Chat, contentDescription = null) },
                    label = { Text("Asistente ARGOS") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onNavigateToChat()
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
    ) {
        Scaffold(
            containerColor = BackgroundWhite,
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard", fontWeight = FontWeight.Bold, color = ArgosGreen) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundWhite, scrolledContainerColor = BackgroundWhite),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Color.DarkGray)
                        }
                    },
                    actions = {
                        Box {
                            IconButton(
                                onClick = { showNotificacionesMenu = !showNotificacionesMenu },
                                modifier = Modifier.padding(end = 8.dp).clip(RoundedCornerShape(50)).background(Color(0xFFFFEBEE))
                            ) {
                                BadgedBox(badge = { Badge(containerColor = Color(0xFFD32F2F)) { Text("3", color = Color.White) } }) {
                                    Icon(Icons.Default.Notifications, contentDescription = "Alertas", tint = Color(0xFFD32F2F))
                                }
                            }

                            DropdownMenu(
                                expanded = showNotificacionesMenu,
                                onDismissRequest = { showNotificacionesMenu = false },
                                modifier = Modifier.background(SurfaceWhite).width(280.dp)
                            ) {
                                Text("Alertas Recientes", fontWeight = FontWeight.ExtraBold, color = ArgosGreen, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp))
                                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                                notificacionesRecientes.forEach { notificacion ->
                                    DropdownMenuItem(
                                        text = { Text(text = notificacion, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark, lineHeight = 18.sp, maxLines = 2, overflow = TextOverflow.Ellipsis) },
                                        onClick = { showNotificacionesMenu = false },
                                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                                    )
                                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                                }

                                DropdownMenuItem(
                                    text = { Text("Ver todo el historial", color = ArgosGreen, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                                    onClick = { showNotificacionesMenu = false },
                                    contentPadding = PaddingValues(vertical = 12.dp)
                                )
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Resumen del Día", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                    Text("Tus cultivos están monitoreados.", fontSize = 14.sp, color = Color.Gray)
                }

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    item { LiveStatCard("Temp. Promedio", "23.5°C", "ÓPTIMO", Icons.Default.Thermostat, Color(0xFFE65100), Color(0xFFFFF3E0)) }
                    item { LiveStatCard("Humedad Global", "68%", "ESTABLE", Icons.Default.Opacity, Color(0xFF0277BD), Color(0xFFE1F5FE)) }
                    item { LiveStatCard("Sensores R4", "12/12", "EN LÍNEA", Icons.Default.WifiTethering, Color(0xFF2E7D32), Color(0xFFE8F5E9)) }
                    item { LiveStatCard("Batería Nodos", "85%", "DESCARGANDO", Icons.Default.BatteryChargingFull, Color(0xFF512DA8), Color(0xFFEDE7F6)) }
                }

                Spacer(modifier = Modifier.height(16.dp))

                SectionHeader(titulo = "Estado Histórico General", icono = Icons.Default.TrendingUp)
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(24.dp), colors = CardDefaults.elevatedCardColors(containerColor = SurfaceWhite)
                ) {
                    // LAS ALTURAS ESTÁN FORZADAS AQUÍ PARA QUE NO DESAPAREZCAN
                    Box(modifier = Modifier.fillMaxWidth().height(240.dp).padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)) {
                        SaludChart()
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                SectionHeader(titulo = "Control de Inspección", icono = Icons.Default.DateRange)
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(24.dp), colors = CardDefaults.elevatedCardColors(containerColor = SurfaceWhite)
                ) {
                    // LAS ALTURAS ESTÁN FORZADAS AQUÍ PARA QUE NO DESAPAREZCAN
                    Box(modifier = Modifier.fillMaxWidth().height(320.dp).padding(16.dp)) {
                        InspeccionCalendar()
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun SectionHeader(titulo: String, icono: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icono, contentDescription = null, tint = Color(0xFF1B5E20), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.DarkGray)
    }
}

@Composable
fun LiveStatCard(titulo: String, valor: String, estado: String, icono: androidx.compose.ui.graphics.vector.ImageVector, iconTint: Color, bgColor: Color) {
    ElevatedCard(
        modifier = Modifier.width(160.dp).height(140.dp).padding(end = 12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp), colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).background(bgColor, shape = RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                    Icon(imageVector = icono, contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp))
                }
                Icon(imageVector = Icons.Default.FiberManualRecord, contentDescription = "Live", tint = if (estado == "Crítico") Color.Red else Color(0xFF4CAF50), modifier = Modifier.size(12.dp))
            }
            Column {
                Text(text = titulo, fontSize = 13.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                Text(text = valor, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF222222))
                Text(text = estado, fontSize = 11.sp, color = iconTint, fontWeight = FontWeight.Bold)
            }
        }
    }
}