package com.cursosant.mviarch.loginModule.model

import com.cursosant.mviarch.R
import com.cursosant.mviarch.commonModule.dataAccess.local.FakeFirebaseAuth
import com.cursosant.mviarch.commonModule.utils.Constants

class LoginRepository(private val auth: FakeFirebaseAuth) {

    suspend fun checkAuth(): LoginState {

       val result = auth.isValidAuth()

        return if (result){

            LoginState.AuthValid
        } else {

            LoginState.Fail(Constants.EC_AUTH, R.string.login_auth_fail)
        }
    }

    suspend fun login(username: String, pin: String): LoginState {

        val result = auth.login(username, pin)

        return if (result){

            LoginState.LoginSuccess
        } else {

            LoginState.Fail(Constants.EC_LOGIN, R.string.login_login_fail)
        }
    }
}