package com.compose.composelearning.ui.base

sealed class State<out T> {

    object Loading : State<Nothing>()

    class Success<T>(val data: T) : State<T>()

    class Error(val exception: Throwable) : State<Nothing>()
}