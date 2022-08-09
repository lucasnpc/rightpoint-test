package com.challenge.whatswrong.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val firstName: String,
    val lastName: String,
    val token: String
)
