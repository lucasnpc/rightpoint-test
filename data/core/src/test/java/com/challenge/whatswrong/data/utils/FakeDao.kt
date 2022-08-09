package com.challenge.whatswrong.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeDao<T>(initialValue: T?) {
    private val _data = MutableStateFlow(initialValue)

    fun queryData(): Flow<T?> = _data

    suspend fun saveData(data: T) {
        _data.emit(data)
    }
}
