package com.riso.defalutapp.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riso.defalutapp.R
import com.riso.defalutapp.ui.EffectConstants
import com.riso.defalutapp.ui.theme.DefalutAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SplashScreen(
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
        Image(
            painter = painterResource(R.drawable.asseco_logo),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
fun SplashPreview() {
    DefalutAppTheme {
        SplashScreen(null) { }
    }
}
