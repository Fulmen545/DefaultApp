package com.riso.defalutapp.utils

import android.content.Context
import com.riso.core.Result

import com.riso.core.FailureFormatter


class FailureFormatterImpl(private val context: Context) : FailureFormatter {

    override fun format(failure: Result.Failure<*>): String {
        return when (failure) {
            is Result.Failure.GeneralFailure -> {
                "GeneralFailure"
            }
            is Result.Failure.RemoteFailure.ConnectionFailure -> {
                "ConnectionFailure"

//                localizedContext().getString(NetworkErrorCodeUi.getStringRes(failure.errorCode))
            }
            is Result.Failure.RemoteFailure.HttpFailure -> {
                "HttpFailure"
            }
        }
    }

    override fun format(throwable: Throwable): String {
//        return if (throwable is NetworkErrorCodeException) {
//            localizedContext().getString(NetworkErrorCodeUi.getStringRes(throwable.networkErrorCode))
//        } else {
//            throwable.localizedMessage ?: localizedContext().getString(
//                NetworkErrorCodeUi.getStringRes(
//                    NetworkErrorCode.GENERAL
//                )
//            )
//        }
        return throwable.localizedMessage ?: "Unknown error"
    }

    override fun format(messageRes: Int): String {
        return context.getString(messageRes)
    }

}
