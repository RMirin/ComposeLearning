package com.compose.composelearning.ui.main

import com.compose.domain.common.State
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.composelearning.ui.theme.ComposeLearningTheme
import com.compose.domain.entities.RecipeModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModels()

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
                BarkHomeContent((uiState.value as State.Success<List<RecipeModel>>).data)
                Log.e("TAG", "ShowItems:")
            }
            is State.Error -> { }
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
fun BarkHomeContent(list: List<RecipeModel>) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
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
fun RecipeListItem(recipe: RecipeModel) {
    var extended by remember { mutableStateOf(false) }
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(start = 16.dp, top = 0.dp, bottom = 8.dp, end = 16.dp)
            .clickable { extended = !extended }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
        ) {
            Column {
                Text(text = recipe.title)
                AnimatedVisibility(visible = !extended) {
                    Text(text = "VIEW DETAIL", color = Blue)
                }
                AnimatedVisibility(visible = extended) {
                    Text(text = recipe.summary)
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeLearningTheme {
//        BarkHomeContent(DataProvider.puppyList)
    }
}