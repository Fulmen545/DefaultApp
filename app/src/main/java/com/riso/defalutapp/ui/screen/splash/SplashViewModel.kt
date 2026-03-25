package com.riso.defalutapp.ui.screen.splash

import androidx.lifecycle.viewModelScope
import com.riso.core.FailureFormatter
import com.riso.core.base.BaseErrorViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.riso.defalutapp.ui.screen.splash.SplashContract.Effect
import com.riso.defalutapp.ui.screen.splash.SplashContract.Event
import com.riso.defalutapp.ui.screen.splash.SplashContract.State

class SplashViewModel(
    failureFormatter: FailureFormatter

) : BaseErrorViewModel<Event, State, Effect>(
    failureFormatter = failureFormatter
) {

    init {
    }

    override fun setInitialState(): State {
        return State()
    }

    override fun handleEvents(event: Event) {
    }

    override fun getErrorSideEffect(errorMessage: String): Effect {
        return Effect.Error(error = errorMessage)
    }

}
