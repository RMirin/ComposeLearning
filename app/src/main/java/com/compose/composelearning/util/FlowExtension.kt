package com.compose.composelearning.util

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.Flow

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