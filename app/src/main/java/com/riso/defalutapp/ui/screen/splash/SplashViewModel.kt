package com.riso.defalutapp.ui.screen.splash

import androidx.lifecycle.viewModelScope
import com.riso.core.FailureFormatter
import com.riso.core.base.BaseErrorViewModel
import com.riso.defalutapp.ui.screen.splash.SplashContract.Effect
import com.riso.defalutapp.ui.screen.splash.SplashContract.Event
import com.riso.defalutapp.ui.screen.splash.SplashContract.State
import com.riso.domain.usecase.InvalidateCachesUseCase
import com.riso.domain.usecase.LoadImageListUseCase
import com.riso.imageloader.api.ImageLoader
import kotlinx.coroutines.launch

class SplashViewModel(
    private val loadImageListUseCase: LoadImageListUseCase,
    private val invalidateCachesUseCase: InvalidateCachesUseCase,
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
        when(event){
            Event.InvalidateCaches -> invalidateCaches()
            Event.Reload -> loadImages()
        }
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

    fun invalidateCaches(){
        viewModelScope.launch {
            invalidateCachesUseCase.execute()
            setState { copy(images = emptyList()) }
            loadImages()
        }
    }

}
