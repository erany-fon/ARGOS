package com.example.my_app_agroberries.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_agroberries.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    // se llama cada vez que el usuario escribe en el campo
    fun onIdentificadorChange(value: String) {
        _uiState.update { it.copy(identificador = value, error = null) }
    }

    fun onContrasenaChange(value: String) {
        _uiState.update { it.copy(contrasena = value, error = null) }
    }

    fun login() {
        viewModelScope.launch {
            // muestra el loading
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = loginUseCase(
                identificador = _uiState.value.identificador.trim(),
                contrasena = _uiState.value.contrasena.trim()
            )

            result.fold(
                onSuccess = { usuario ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginExitoso = true,
                            idUsuario = usuario.idUsuario
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
            )
        }
    }
}