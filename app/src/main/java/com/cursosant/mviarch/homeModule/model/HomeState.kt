package com.cursosant.mviarch.homeModule.model

import com.cursosant.mviarch.commonModule.entities.Wine

sealed class HomeState {

    data object Init: HomeState()

    data object ShowProgress: HomeState()

    data object HideProgress: HomeState()

    data class RequestWineSucess(val list: List<Wine>): HomeState()

    data class AddWineSuccess(val msgRes: Int): HomeState()

    data class Fail(val code: Int, val msgRes: Int): HomeState()
}