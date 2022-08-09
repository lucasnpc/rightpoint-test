package com.challenge.whatswrong.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.whatswrong.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<State>(State.Idle)
    val uiState: StateFlow<State> = _uiState

    fun login(userName: CharSequence?, password: CharSequence?) = viewModelScope.launch {
        _uiState.emit(State.Loading)
        try {
            val session = repository.login(
                userName = userName?.toString().orEmpty(),
                password = password?.toString().orEmpty()
            )
            _uiState.emit(State.LoginSucceeded(session))
        } catch (e: Exception) {
            println(e.message)
            _uiState.emit(State.LoginFailed(e))
        }
    }
}
