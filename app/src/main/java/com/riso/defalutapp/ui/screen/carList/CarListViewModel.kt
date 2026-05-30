package com.riso.defalutapp.ui.screen.carList

import androidx.lifecycle.viewModelScope
import com.riso.core.FailureFormatter
import com.riso.core.base.StateFlowBaseErrorViewModel
import com.riso.defalutapp.ui.screen.carList.CarListContract.Effect
import com.riso.defalutapp.ui.screen.carList.CarListContract.Event
import com.riso.defalutapp.ui.screen.carList.CarListContract.State
import com.riso.domain.usecase.InvalidateCachesUseCase
import com.riso.domain.usecase.LoadImageListUseCase
import kotlinx.coroutines.launch

class CarListViewModel(
    private val loadImageListUseCase: LoadImageListUseCase,
    private val invalidateCachesUseCase: InvalidateCachesUseCase,
    failureFormatter: FailureFormatter

) : StateFlowBaseErrorViewModel<Event, State, Effect>(
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
