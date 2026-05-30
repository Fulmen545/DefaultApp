package com.riso.core.base

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.riso.core.Result
import com.riso.core.FailureFormatter
import kotlinx.coroutines.launch

abstract class StateFlowBaseErrorViewModel<Event, UiState, Effect>(
    protected val failureFormatter: FailureFormatter
) : StateFlowBaseViewModel<Event, UiState, Effect>() where Event : ViewEvent, UiState : LoadingState<UiState>, Effect : ViewSideEffect {

    abstract fun getErrorSideEffect(errorMessage: String): Effect

    /**
     * Method to automatically handle showing the progress bar and error during an usecase execution
     * @param useCase - useCase to execute
     * @param messageRes - sometimes we want to support different message in one screen, if @see[messageRes] is defined then use it otherwise copy the message res from the state
     * @param showLoading - indication if we want to show the loading to the user
     * @param onSuccess - what should happen when usecase is successful
     *
     */
    fun <T> executeUseCase(
        useCase: suspend () -> Result<T>,
        @StringRes messageRes: Int? = null,
        showLoading: Boolean = true,
        onFailure: (Result.Failure<*>) -> Unit = {
            val message = failureFormatter.format(it)
            setEffect { getErrorSideEffect(errorMessage = message) }
        },
        onSuccess: (t: T) -> Unit
    ) {
        viewModelScope.launch {
            executeUseCaseInternal(
                useCase = useCase,
                messageRes = messageRes,
                showLoading = showLoading,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }
    }

    suspend fun <T> executeUseCaseSuspend(
        useCase: suspend () -> Result<T>,
        @StringRes messageRes: Int? = null,
        showLoading: Boolean = true,
        onFailure: (Result.Failure<*>) -> Unit = {
            val message = failureFormatter.format(it)
            setEffect { getErrorSideEffect(errorMessage = message) }
        },
        onSuccess: (t: T) -> Unit
    ) {
        executeUseCaseInternal(
            useCase = useCase,
            messageRes = messageRes,
            showLoading = showLoading,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    private suspend fun <T> executeUseCaseInternal(
        useCase: suspend () -> Result<T>,
        @StringRes messageRes: Int? = null,
        showLoading: Boolean,
        onSuccess: (t: T) -> Unit,
        onFailure: (Result.Failure<*>) -> Unit
    ) {
        if (showLoading) {
            setState {
                copyState(
                    isLoading = true,
                    messageRes = messageRes ?: viewState.value.messageRes
                )
            }
        }

        val result = useCase()

        if (showLoading) {
            setState { copyState(false, messageRes = messageRes ?: viewState.value.messageRes) }
        }

        when (result) {
            is Result.Failure<*> -> {
                onFailure(result)
            }
            is Result.Success -> {
                onSuccess(result.value)
            }
        }
    }
}
