package com.cursosant.mviarch.loginModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursosant.mviarch.loginModule.intent.LoginIntent
import com.cursosant.mviarch.loginModule.model.LoginRepository
import com.cursosant.mviarch.loginModule.model.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: LoginRepository): ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Init)
    val state: StateFlow<LoginState> = _state

    val channel = Channel<LoginIntent>(Channel.UNLIMITED)

    init {
        setupIntent()
    }

    private fun setupIntent() {

        viewModelScope.launch {
            channel.consumeAsFlow()
                .collect{ i ->
                    when(i) {

                        is LoginIntent.CheckAuth -> checkAuth()
                        is LoginIntent.Login -> login(i.username, i.pin)
                    }
                }
        }
    }

    private suspend fun login(username: String, pin: String) {

        _state.value = LoginState.ShowProgress

        try {
            withContext(Dispatchers.IO){
                _state.value = repository.login(username, pin)
            }
        } finally {
            _state.value = LoginState.HideProgress
        }

    }

    private suspend fun checkAuth() {

        _state.value = LoginState.ShowProgress

        try {
            withContext(Dispatchers.IO){
                _state.value = repository.checkAuth()
            }
        } finally {
            _state.value = LoginState.HideProgress
        }
    }
}