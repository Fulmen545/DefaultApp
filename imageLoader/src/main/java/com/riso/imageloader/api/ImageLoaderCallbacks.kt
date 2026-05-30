package com.riso.imageloader.api

fun interface OnSuccessCallback {
    fun onSuccess()
}

fun interface OnErrorCallback {
    fun onError(throwable: Throwable)
}