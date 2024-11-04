package com.cursosant.mviarch.favouriteModule.model

import com.cursosant.mviarch.accountModule.model.AccountState
import com.cursosant.mviarch.commonModule.entities.FirebaseUser
import com.cursosant.mviarch.commonModule.entities.Wine

sealed class FavouriteState {

    data object Init: FavouriteState()

    data object ShowProgress: FavouriteState()

    data object HideProgress: FavouriteState()

    data class RequestWinesSuccess(val list: List<Wine>): FavouriteState()

    data class AddWineSuccess(val msgResult: Int): FavouriteState()

    data class DeleteWineSuccess(val msgResult: Int): FavouriteState()

    data class Fail(val code: Int, val msgRes: Int): FavouriteState()
}