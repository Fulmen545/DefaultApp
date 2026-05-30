package com.riso.defalutapp.ui.screen.splash

import androidx.collection.FloatList
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.riso.defalutapp.R
import com.riso.defalutapp.ui.EffectConstants
import com.riso.defalutapp.ui.theme.DefalutAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SplashScreen(
    state: SplashContract.State,
    effectFlow: Flow<SplashContract.Effect>?,
    onNavigationEffect: (effect: SplashContract.Effect) -> Unit,
) {

    LaunchedEffect(EffectConstants.LAUNCH_LISTEN_FOR_EFFECTS) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is SplashContract.Effect.Navigate -> {
                    onNavigationEffect(effect)
                }

                is SplashContract.Effect.Error -> {
                    //TODO handle error
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
                modifier = Modifier.size(512.dp),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        } else {
            Column{
                Spacer(modifier = Modifier.size(24.dp))
                LazyColumn(contentPadding = PaddingValues(vertical = 12.dp)) {
                    items(state.images.size) { index ->
                        AsyncImage(
                            model = state.images[index].url,
                            contentDescription = state.images[index].id.toString(),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SplashPreview() {
    DefalutAppTheme {
        SplashScreen(
            state = SplashContract.State(),
            effectFlow = null,
            onNavigationEffect = {}
        )
    }
}
