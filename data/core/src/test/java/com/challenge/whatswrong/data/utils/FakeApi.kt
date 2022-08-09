package com.challenge.whatswrong.data.utils

class FakeApi<T>(
    private val value: T,
    private val error: Throwable? = null
) {
    suspend fun fetchData(): T = if (error == null) value else throw error
}
