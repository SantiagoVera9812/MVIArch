package com.cursosant.mviarch.homeModule.intent

import com.cursosant.mviarch.commonModule.entities.Wine
import com.cursosant.mviarch.favouriteModule.intent.FavouriteIntent

sealed class HomeIntent {

    data object RequestWines: HomeIntent()

    data class AddWine(val wine: Wine): HomeIntent()


}