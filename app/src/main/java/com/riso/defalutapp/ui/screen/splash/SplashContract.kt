package com.riso.defalutapp.ui.screen.splash

import com.riso.core.base.LoadingState
import com.riso.core.base.ViewEvent
import com.riso.core.base.ViewSideEffect
import com.riso.core.base.ViewState
import com.riso.defalutapp.ui.Screen
import com.riso.domain.model.ImageDataModel

class SplashContract {

    data class State(
        override val isLoading: Boolean = false,
        override val messageRes: Int? = null,
        val images: List<ImageDataModel> = emptyList()
    ) : LoadingState<State> {
        override fun copyState(isLoading: Boolean, messageRes: Int?): State {
            return copy(isLoading = isLoading, messageRes = messageRes, images = images)
        }
    }

    sealed class Event : ViewEvent

    sealed class Effect : ViewSideEffect {
        data class Error(val error: String) : Effect()
        data class Navigate(val screen: Screen) : Effect()
    }
}