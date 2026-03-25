package com.riso.core

import androidx.annotation.StringRes

/**
 * Format a failure to the string
 */
interface FailureFormatter {

    fun format(failure: Result.Failure<*>): String

    fun format(throwable: Throwable): String

    /**
     * get the string from the string res
     */
    fun format(@StringRes messageRes: Int): String
}
