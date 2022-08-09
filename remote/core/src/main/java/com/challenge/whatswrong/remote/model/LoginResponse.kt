package com.challenge.whatswrong.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "firstname") val firstName: String,
    @Json(name = "lastname") val lastName: String,
    @Json(name = "token") val token: String
)
