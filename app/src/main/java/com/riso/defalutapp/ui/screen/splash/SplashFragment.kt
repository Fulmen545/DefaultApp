package com.riso.defalutapp.ui.screen.splash

import androidx.compose.runtime.Composable
import com.asseco.ce.parking.ui.base.BaseComposeFragment
import com.riso.defalutapp.ui.Screen
import com.riso.defalutapp.ui.navigate

import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseComposeFragment() {

    private val viewModel by viewModel<SplashViewModel>()

    @Composable
    override fun SetContent() {
        SplashScreen(
            state = viewModel.viewState.value,
            effectFlow = viewModel.effect,
            onEventSent = { viewModel.setEvent(it)},
            onNavigationEffect = ::onNavigationEvent,
        )
    }

    private fun onNavigationEvent(effect: SplashContract.Effect) {
        when (effect) {
            is SplashContract.Effect.Navigate -> {
                navigate(from = Screen.Splash, to = effect.screen)
            }
            else -> {}
        }
    }
}