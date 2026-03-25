package com.riso.core

/**
 * A discriminated union that encapsulates a successful outcome with a value of type [T]
 * or a failure with an arbitrary [Throwable] exception.
 */
sealed class Result<out T> {

    companion object {
        fun <T> success(value: T): Success<T> = Success(value)

        fun <E : Throwable> connectionFailure(
            cause: E
        ) = Failure.RemoteFailure.ConnectionFailure(cause)

        fun <E : Throwable> httpFailure(
            code: Int,
            cause: E
        ) = Failure.RemoteFailure.HttpFailure(code, cause)

        fun <E : Throwable> generalFailure(
            cause: E
        ) = Failure.GeneralFailure(cause)
    }

    fun isSuccess() = this is Success

    fun isFailure() = this is Failure<*>

    fun getOrThrow(): T {
        when (this) {
            is Success -> return this.value
            is Failure<*> -> throw this.cause
        }
    }

    fun exceptionOrNull(): Throwable? {
        return when (this) {
            is Success -> null
            is Failure<*> -> this.cause
        }
    }

    data class Success<out T>(val value: T) : Result<T>()

    sealed class Failure<out E : Throwable>(
        open val cause: E
    ) : Result<Nothing>() {

        sealed class RemoteFailure<out E : Throwable>(
            override val cause: E
        ) : Failure<E>(cause) {

            data class ConnectionFailure<out E : Throwable>(
                override val cause: E
            ) : RemoteFailure<E>(cause)

            data class HttpFailure<out E : Throwable>(
                val code: Int,
                override val cause: E
            ) : RemoteFailure<E>(cause)
        }

        data class GeneralFailure<out E : Throwable>(
            override val cause: E
        ) : Failure<E>(cause)
    }
}

inline fun <T> Result<T>.onSuccess(
    action: (T) -> Unit
): Result<T> = also {
    if (this is Result.Success) {
        action(value)
    }
}

inline fun <T> Result<T>.onFailure(
    action: (Throwable) -> Unit
): Result<T> = also {
    if (this is Result.Failure<Throwable>) {
        action(cause)
    }
}

inline fun <T> Result<T>.onFailed(
    action: (Result.Failure<Throwable>) -> Nothing
): T = when (this) {
    is Result.Success -> value
    is Result.Failure<Throwable> -> action(this)
}

/*inline fun <T> runCatching(block: () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: Exception) {
        Result.Failure(e)
    }
}*/

inline fun <R, T> Result<T>.map(transform: (value: T) -> R): Result<R> {
    return when (this) {
        is Result.Success<T> -> Result.Success(transform(value))
        is Result.Failure<*> -> {
            when (this) {
                is Result.Failure.GeneralFailure<*> -> this
                is Result.Failure.RemoteFailure.ConnectionFailure<*> -> this
                is Result.Failure.RemoteFailure.HttpFailure<*> -> this
            }
        }
    }
}
