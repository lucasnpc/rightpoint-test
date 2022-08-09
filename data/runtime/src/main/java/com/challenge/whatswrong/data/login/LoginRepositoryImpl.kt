package com.challenge.whatswrong.data.login

import com.challenge.whatswrong.data.model.UserSession
import com.challenge.whatswrong.data.repository.LoginRepository
import com.challenge.whatswrong.remote.model.LoginRequest
import com.challenge.whatswrong.remote.services.LoginService
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRepository {
    override suspend fun login(userName: String, password: String): UserSession {
        val request = LoginRequest(
            username = userName,
            password = password
        )
        val response = loginService.login(request)

        return UserSession(
            sessionId = response.token,
            firstName = response.firstName,
            lastName = response.lastName
        )
    }
}
