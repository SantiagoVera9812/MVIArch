package com.cursosant.mviarch.favouriteModule.intent

import com.cursosant.mviarch.commonModule.entities.Wine

sealed class FavouriteIntent {


    data object RequestWines: FavouriteIntent()

    data class AddWine(val wine: Wine): FavouriteIntent()

    data class DeleteWine(val wine: Wine): FavouriteIntent()

}