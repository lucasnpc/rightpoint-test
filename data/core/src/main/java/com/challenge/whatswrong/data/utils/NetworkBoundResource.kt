package com.challenge.whatswrong.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <RemoteType, CachedType> networkBoundResource(
    crossinline query: () -> Flow<CachedType>,
    crossinline fetch: suspend () -> RemoteType,
    crossinline save: suspend (RemoteType) -> Unit,
    crossinline shouldFetch: (CachedType?) -> Boolean = { true }
) = flow {
    val data = query().firstOrNull()

    val result = if (shouldFetch(data)) {
        emit(Resource.Loading)
        try {
            save(fetch())
            query().map { Resource.Success(it) }
        } catch (e: Exception) {
            query().map { Resource.Failure(e) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(result)
}

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val error: Throwable) : Resource<Nothing>()
}
