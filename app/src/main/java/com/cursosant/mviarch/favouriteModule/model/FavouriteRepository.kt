package com.cursosant.mviarch.favouriteModule.model

import com.cursosant.mviarch.R
import com.cursosant.mviarch.commonModule.entities.Wine
import com.cursosant.mviarch.commonModule.utils.Constants

class FavouriteRepository(private val db: RoomDatabase) {

    fun getAllWines(): FavouriteState {

        val result = db.getAllWines()

        return FavouriteState.RequestWinesSuccess(result)
//        return if (result.isNotEmpty()) {
//
//            FavouriteState.RequestWinesSuccess(result)
//        } else {
//            FavouriteState.Fail(Constants.EC_REQUEST_NO_WINES, R.string.room_request_fail)
//        }
    }

    fun addWine(wine: Wine): FavouriteState {

        val result = db.addWine(wine)

        if (result == -1L) {
           return FavouriteState.Fail(Constants.EC_SAVE_WINE, R.string.room_save_fail)
        } else {

            return FavouriteState.AddWineSuccess(R.string.room_save_success)
        }
    }

    fun deleteWine(wine: Wine): FavouriteState {

        val result = db.deleteWine(wine)

        if (result == 0) {
            return FavouriteState.Fail(Constants.EC_SAVE_WINE, R.string.room_save_fail)
        } else {

            return FavouriteState.DeleteWineSuccess(R.string.room_save_success)
        }
    }
}