package com.compose.composelearning.ui.main

import com.compose.composelearning.util.Event
import com.compose.domain.common.State
import android.os.Bundle
import android.os.RemoteException
import android.widget.TextView
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
import com.compose.domain.model.RecipeModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.compose.composelearning.BuildConfig
import com.compose.data.exception.ClientException
import com.compose.data.exception.NoInternetException
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeLearningTheme {
                val isLoading by mainViewModel.uiState.collectAsState()
                val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading == State.Loading)
                SwipeRefresh(state = swipeRefreshState, onRefresh = mainViewModel::getData) {
                    Scaffold(modifier = Modifier.fillMaxSize()
                        , backgroundColor = colorResource(id = R.color.purple_200)
                    ) { padding ->
                        LoadAndShowItems(model = mainViewModel, padding = padding)
                    }
                }
            }
        }
    }

    @ExperimentalAnimationApi
    @Composable
    fun LoadAndShowItems(model: MainViewModel, padding: PaddingValues) {
        val uiState = model.uiState.collectAsState()
        // Mount your UI in according to uiState object
        when (uiState.value) {
            is State.Success -> {
                BarkHomeContent((uiState.value as State.Success<List<RecipeModel>>).data)
            }
            is State.Error -> {
                Toast.makeText(
                    applicationContext,
                    getBasicErrorMsg(throwable = (uiState.value as State.Error).exception),
                    Toast.LENGTH_SHORT
                ).show()
                ReloadButton(model)
            }
            is State.Loading -> {
//                SimpleCircularProgressIndicator()
            }
        }
        // Launch a coroutine when the component is first launched
        LaunchedEffect(model) {
            // this call should change uiState internally in your viewModel
            model.getData()
        }
    }

    @Composable
    private fun getBasicErrorMsg(throwable: Throwable): String {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
        }
        val context = LocalContext.current
//        viewModelScope.launch {
        return when (throwable) {
            is RemoteException -> throwable.message ?: context.getString(R.string.alert_undefined)
            is NoInternetException -> context.getString(R.string.alert_no_internet)
            is ClientException -> throwable.error
            else -> context.getString(when (throwable) {
                is UnknownHostException,
                is SocketTimeoutException,
                is ConnectException -> R.string.alert_no_server_connection
                else -> R.string.alert_undefined
            })
//            })
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
                        Html(text = recipe.summary)
                    }
                }
            }
        }
    }
}

//@Composable
//fun SimpleCircularProgressIndicator() {
//    Column(modifier = Modifier
//        .fillMaxWidth()
//        .fillMaxHeight(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally) {
//        CircularProgressIndicator()
//    }
//}

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

@Composable
fun Html(text: String) {
    AndroidView(factory = { context ->
        TextView(context).apply {
            setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
        }
    })
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