package com.compose.composelearning.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.compose.composelearning.data.dto.RecipeDto
import com.compose.composelearning.domain.MainUseCase
import com.compose.composelearning.ui.base.BaseViewModel
import com.compose.composelearning.ui.base.State
import com.compose.composelearning.util.doOnError
import com.compose.composelearning.util.doOnLoading
import com.compose.composelearning.util.doOnSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainUseCase: MainUseCase,
    private val app: Application
): BaseViewModel(app) {

    private val _uiState = MutableStateFlow<State<List<RecipeDto>>>(State.Loading)
    val uiState: StateFlow<State<List<RecipeDto>>>
        get() = _uiState

    fun getData() = mainUseCase.getData()
        .doOnLoading {
            showErrorState.set(true)

        }
        .doOnSuccess { dto ->
            _uiState.value = State.Success(dto.recipes)
        }
        .doOnError { error ->
            showErrorState.set(false)
            showBasicError(error)
        }
        .launchIn(viewModelScope)
}