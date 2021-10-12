package com.compose.composelearning.util

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.launchWhenStarted(
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    action: (T) -> Unit
) {
    lifecycleCoroutineScope.launchWhenStarted {
        this@launchWhenStarted.collect {
            action.invoke(it)
        }
    }
}