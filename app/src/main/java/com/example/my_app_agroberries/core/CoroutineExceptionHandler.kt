package com.example.my_app_agroberries.core

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

object GlobalCoroutineExceptionHandler {
    val handler: CoroutineContext = CoroutineExceptionHandler { _, exception ->
        Log.e("GlobalException", "Uncaught coroutine exception", exception)
    }
}

