package com.compose.composelearning.util

import com.compose.domain.common.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun <T> Flow<State<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<State<T>> =
    transform { value ->
        if (value is State.Success) {
            action(value.data)
        }
        return@transform emit(value)
    }

fun <T> Flow<State<T>>.doOnError(action: suspend (Throwable) -> Unit): Flow<State<T>> =
    transform { value ->
        if (value is State.Error) {
            action(value.exception)
        }
        return@transform emit(value)
    }

fun <T> Flow<State<T>>.doOnLoading(action: suspend () -> Unit): Flow<State<T>> =
    transform { value ->
        if (value is State.Loading) {
            action()
        }
        return@transform emit(value)
    }