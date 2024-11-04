package com.cursosant.mviarch.accountModule.intent

sealed class AccountIntent {

    data object RequestUser: AccountIntent()
    data object SignOut: AccountIntent()

}