package com.challenge.whatswrong.remote.services

import com.challenge.whatswrong.remote.model.LoginRequest
import com.challenge.whatswrong.remote.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}
