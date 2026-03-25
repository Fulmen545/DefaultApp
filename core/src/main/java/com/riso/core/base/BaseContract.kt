package com.riso.core.base

interface ViewState

interface LoadingState<T> : ViewState {
    val isLoading: Boolean
    val messageRes: Int?

    fun copyState(isLoading: Boolean, messageRes: Int?): T
}

interface ViewEvent

interface ViewSideEffect
