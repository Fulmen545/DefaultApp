package com.riso.defalutapp.ui.screen.splash

import com.riso.core.FailureFormatter
import com.riso.core.base.BaseErrorViewModel
import com.riso.defalutapp.ui.screen.splash.SplashContract.Effect
import com.riso.defalutapp.ui.screen.splash.SplashContract.Event
import com.riso.defalutapp.ui.screen.splash.SplashContract.State
import com.riso.domain.usecase.LoadImageListUseCase

class SplashViewModel(
    private val loadImageListUseCase: LoadImageListUseCase,
    failureFormatter: FailureFormatter

) : BaseErrorViewModel<Event, State, Effect>(
    failureFormatter = failureFormatter
) {

    init {
        loadImages()
    }

    override fun setInitialState(): State {
        return State()
    }

    override fun handleEvents(event: Event) {
    }

    override fun getErrorSideEffect(errorMessage: String): Effect {
        return Effect.Error(error = errorMessage)
    }

    fun loadImages(){
        executeUseCase(
            useCase = { loadImageListUseCase.execute() },
            onSuccess = { setState { copy(images = it) } }
        )
    }

}
