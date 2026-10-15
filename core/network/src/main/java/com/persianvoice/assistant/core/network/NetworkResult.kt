package com.persianvoice.assistant.core.network

/**
 * نتیجه Network - جایگزین ساده‌تر از Result<T> برای لایه data.
 */
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure(val error: Throwable, val code: Int? = null) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
}

inline fun <T, R> NetworkResult<T>.map(transform: (T) -> R): NetworkResult<R> = when (this) {
    is NetworkResult.Success -> NetworkResult.Success(transform(data))
    is NetworkResult.Failure -> NetworkResult.Failure(error, code)
NetworkResult.Loading -> NetworkResult.Loading
}

fun Throwable.toNetworkFailure(): NetworkResult.Failure =
    NetworkResult.Failure(this, code = null)
