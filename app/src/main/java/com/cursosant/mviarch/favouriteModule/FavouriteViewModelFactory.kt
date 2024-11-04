package com.cursosant.mviarch.favouriteModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cursosant.mviarch.accountModule.model.AccountRepository
import com.cursosant.mviarch.favouriteModule.model.FavouriteRepository


class FavouriteViewModelFactory(private val repository: FavouriteRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FavouriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase de view model desconocida")
    }
}