package com.challenge.whatswrong.login

import com.challenge.whatswrong.data.model.UserSession

sealed class State {
    object Idle : State()
    object Loading : State()
    data class LoginSucceeded(val session: UserSession) : State()
    data class LoginFailed(val exception: Exception) : State()
}
