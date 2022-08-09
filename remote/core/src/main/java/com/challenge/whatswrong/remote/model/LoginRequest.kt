package com.challenge.whatswrong.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "user_name") val userName: String,
    @Json(name = "password") val password: String
)
