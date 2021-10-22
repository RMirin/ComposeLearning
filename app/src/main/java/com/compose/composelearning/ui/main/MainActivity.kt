package com.compose.composelearning.ui.main

import com.compose.domain.common.State
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.composelearning.R
import com.compose.composelearning.ui.theme.ComposeLearningTheme
import com.compose.domain.entities.RecipeModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.platform.LocalContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mainViewModel: MainViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeLearningTheme {
                Scaffold(modifier = Modifier.fillMaxSize()
                    , backgroundColor = colorResource(id = R.color.purple_200)
                ) {
                    LoadAndShowItems(model = mainViewModel)
                }
            }
        }
    }

    @ExperimentalAnimationApi
    @Composable
    fun LoadAndShowItems(model: MainViewModel) {
        val uiState = model.uiState.collectAsState()
        // Mount your UI in according to uiState object
        when (uiState.value) {
            is State.Success -> {
                BarkHomeContent((uiState.value as State.Success<List<RecipeModel>>).data)
            }
            is State.Error -> {
                Toast.makeText(
                    LocalContext.current,
                    model.errorMessage.value?.getContentIfNotHandled(),
                    Toast.LENGTH_SHORT
                ).show()
                ReloadButton(model)
            }
            is State.Loading -> {
                SimpleCircularProgressIndicator()
            }
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
        modifier = Modifier
            .padding(start = 16.dp, top = 0.dp, bottom = 8.dp, end = 16.dp)
            .clickable { extended = !extended }
            .wrapContentHeight(Alignment.CenterVertically),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Box(
            modifier = Modifier.background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        colorResource(R.color.orange)
                    )
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),

            ) {
                Column {
                    Text(text = recipe.title)
                    AnimatedVisibility(visible = !extended) {
                        Text(text = "View detail", color = Blue)
                    }
                    AnimatedVisibility(visible = extended) {
                        Text(text = recipe.summary)
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleCircularProgressIndicator() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
    }
}

@Composable
fun ReloadButton(viewModel: MainViewModel) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { viewModel.getData() }, colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.Cyan
        )) {
            Text("reload", color = Color.White)
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeLearningTheme {
        val list = mutableListOf<RecipeModel>()
        list.add(RecipeModel(title = "title", summary = "summary"))
        list.add(RecipeModel(title = "title", summary = "summary"))
        list.add(RecipeModel(title = "title", summary = "summary"))
        list.add(RecipeModel(title = "title", summary = "summary"))
        BarkHomeContent(list)
    }
}