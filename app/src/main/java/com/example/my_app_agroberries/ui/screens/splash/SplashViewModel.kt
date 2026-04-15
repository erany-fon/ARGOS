package com.example.my_app_agroberries.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    init {
        // cuando se crea el ViewModel arranca el timer
        checkReady()
    }

    private fun checkReady() {
        viewModelScope.launch {
            delay(2000L) // 2 segundos de splash
            _isReady.value = true
        }
    }
}