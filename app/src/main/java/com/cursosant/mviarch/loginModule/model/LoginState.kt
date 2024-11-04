package com.cursosant.mviarch.loginModule.model

import com.cursosant.mviarch.commonModule.entities.Wine

sealed class LoginState {

    data object Init: LoginState()
    data object ShowProgress: LoginState()
    data object HideProgress: LoginState()
    data class Fail(val code: Int, val msgRes: Int): LoginState()
    data object AuthValid: LoginState()
    data object LoginSuccess: LoginState()
}