package com.example.my_app_agroberries.ui.screens.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.my_app_agroberries.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (Int) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    var contrasenaVisible by remember { mutableStateOf(false) }

    // Controladores de estado responsivos
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(uiState.loginExitoso) {
        if (uiState.loginExitoso) onLoginSuccess(uiState.idUsuario)
    }

    // Colores base de la marca
    val argosNavy = Color(0xFF0A0A0A)
    val argosTeal = Color(0xFF00ACC1)
    val argosGreen = Color(0xFF689F38)
    val argosBackground = Color(0xFFF7F9F7)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = argosBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .imePadding() // ¡Magia! Evita que el teclado tape el botón
                .verticalScroll(scrollState) // ¡Magia! Permite hacer scroll si la pantalla es pequeña
                .padding(vertical = if (isLandscape) 16.dp else 0.dp), // Margen extra solo en horizontal
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Header con Logo - Se hace más pequeño si está en horizontal
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Argos",
                modifier = Modifier.size(if (isLandscape) 90.dp else 170.dp)
            )

            Spacer(modifier = Modifier.height(if (isLandscape) 8.dp else 16.dp))

            Text(
                text = "ARGOS",
                fontSize = if (isLandscape) 24.sp else 32.sp, // Letra más pequeña en horizontal
                fontWeight = FontWeight.ExtraBold,
                color = argosNavy,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 40.dp))

            // Configuración de colores para los campos de texto
            val textFieldColors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = argosNavy,
                unfocusedTextColor = argosNavy,
                focusedBorderColor = argosTeal,
                unfocusedBorderColor = argosNavy.copy(alpha = 0.3f),
                focusedLabelColor = argosTeal,
                unfocusedLabelColor = argosNavy.copy(alpha = 0.6f),
                cursorColor = argosTeal,
                errorBorderColor = Color(0xFFE65100)
            )

            // Contenedor blanco para el formulario
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.identificador,
                        onValueChange = viewModel::onIdentificadorChange,
                        label = { Text("Usuario o Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = textFieldColors,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                            autoCorrect = false
                        ),
                        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                        isError = uiState.error != null
                    )

                    Spacer(modifier = Modifier.height(if (isLandscape) 12.dp else 20.dp))

                    OutlinedTextField(
                        value = uiState.contrasena,
                        onValueChange = viewModel::onContrasenaChange,
                        label = { Text("Contraseña") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = textFieldColors,
                        visualTransformation = if (contrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(); viewModel.login() }),
                        trailingIcon = {
                            TextButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                                Text(
                                    text = if (contrasenaVisible) "Ocultar" else "Ver",
                                    color = argosNavy,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        },
                        isError = uiState.error != null
                    )

                    if (uiState.error != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.error!!,
                            color = Color(0xFFE65100),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 32.dp))

                    // Botón con degradado personalizado
                    Button(
                        onClick = { focusManager.clearFocus(); viewModel.login() },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
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
                                    text = "Iniciar Sesión",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}