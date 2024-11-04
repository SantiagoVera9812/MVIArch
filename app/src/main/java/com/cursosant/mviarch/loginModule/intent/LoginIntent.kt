package com.cursosant.mviarch.loginModule.intent

sealed class LoginIntent {

    data object CheckAuth: LoginIntent()

    data class Login(val username: String, val pin: String): LoginIntent()
}