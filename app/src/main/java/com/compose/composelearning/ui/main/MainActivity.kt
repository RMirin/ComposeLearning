package com.compose.composelearning.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.composelearning.data.dto.RecipeDto
import com.compose.composelearning.ui.DataProvider
import com.compose.composelearning.ui.base.State
import com.compose.composelearning.ui.theme.ComposeLearningTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModel()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeLearningTheme {
                ShowItems(model = mainViewModel)
            }
        }
    }

    @ExperimentalAnimationApi
    @Composable
    fun ShowItems(model: MainViewModel){
        val uiState = model.uiState.collectAsState()
        // Mount your UI in according to uiState object
        when (uiState.value) {
            is State.Success -> {
                BarkHomeContent((uiState.value as State.Success<List<RecipeDto>>).data)
                Log.e("TAG", "ShowItems:")
            }
            is State.Error -> {  }
            is State.Loading -> { }
        }
        // Launch a coroutine when the component is first launched
        LaunchedEffect(model) {
            // this call should change uiState internally in your viewModel
            model.getData()
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun BarkHomeContent(list: List<RecipeDto>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = list,
            itemContent = {
                RecipeListItem(recipe = it)
            })
    }
}

@ExperimentalAnimationApi
@Composable
fun RecipeListItem(recipe: RecipeDto) {
    Row {
        var extended by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.clickable { extended = !extended }
        ) {
            Text(text = recipe.title)
            Text(text = "VIEW DETAIL")
            AnimatedVisibility(visible = extended) {
                Text(text = recipe.summary)
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeLearningTheme {
        BarkHomeContent(DataProvider.puppyList)
    }
}