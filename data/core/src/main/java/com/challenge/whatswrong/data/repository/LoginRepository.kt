package com.challenge.whatswrong.data.repository

import com.challenge.whatswrong.data.model.UserSession

interface LoginRepository {
    suspend fun login(userName: String, password: String): UserSession
}
