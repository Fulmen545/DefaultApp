package com.riso.defalutapp.ui.screen.splash

import android.widget.ImageView
import androidx.collection.FloatList
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.riso.defalutapp.R
import com.riso.defalutapp.ui.EffectConstants
import com.riso.defalutapp.ui.theme.DefalutAppTheme
import com.riso.imageloader.api.ImageLoader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun SplashScreen(
    state: SplashContract.State,
    effectFlow: Flow<SplashContract.Effect>?,
    onEventSent: (event: SplashContract.Event) -> Unit,
    onNavigationEffect: (effect: SplashContract.Effect) -> Unit,
) {
    val imageLoader: ImageLoader = koinInject()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(EffectConstants.LAUNCH_LISTEN_FOR_EFFECTS) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is SplashContract.Effect.Navigate -> {
                    onNavigationEffect(effect)
                }

                is SplashContract.Effect.Error -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(message = effect.error)
                    }
                }
            }
        }?.collect()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        if (state.images.isEmpty()) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                modifier = Modifier.size(512.dp).clickable(onClick = { onEventSent(SplashContract.Event.Reload) }),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        } else {
            Column {
                Spacer(modifier = Modifier.size(36.dp))
                LazyColumn(contentPadding = PaddingValues(vertical = 12.dp)) {
                    items(state.images.size) { index ->
                        Box(
                            modifier = Modifier.border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                        ) {
                            Column() {
                                ImageLoaderView(
                                    url = state.images[index].url,
                                    imageLoader = imageLoader,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = state.images[index].id.toString(),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                            }
                        }
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp)
        )

        FloatingActionButton(
            onClick = { onEventSent(SplashContract.Event.InvalidateCaches) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(start = 16.dp, end = 16.dp, bottom = 46.dp)
        ) {
            Text(text = "Invalidate Caches", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun ImageLoaderView(
    url: String,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context -> ImageView(context) },
        modifier = modifier,
        update = { imageView ->
            imageLoader.load(
                url = url,
                placeholderRes = R.drawable.ic_launcher_foreground,
                target = imageView
            )
        }
    )
}

@Preview
@Composable
fun SplashPreview() {
    DefalutAppTheme {
        SplashScreen(
            state = SplashContract.State(),
            effectFlow = null,
            onEventSent = {},
            onNavigationEffect = {}
        )
    }
}
