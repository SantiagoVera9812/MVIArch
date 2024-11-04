package com.cursosant.mviarch.favouriteModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursosant.mviarch.commonModule.entities.Wine
import com.cursosant.mviarch.favouriteModule.intent.FavouriteIntent
import com.cursosant.mviarch.favouriteModule.model.FavouriteRepository
import com.cursosant.mviarch.favouriteModule.model.FavouriteState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteViewModel(private val repository: FavouriteRepository): ViewModel() {
    private val _state = MutableStateFlow<FavouriteState>(FavouriteState.Init)
    val state: StateFlow<FavouriteState> = _state

    val channel = Channel<FavouriteIntent> {Channel.UNLIMITED}

    init {
        setUpIntent()
    }

    private fun setUpIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow()
                .collect{ i ->
                    when(i) {

                        is FavouriteIntent.RequestWines -> getAllWines()
                        is FavouriteIntent.AddWine -> addWine(i.wine)
                        is FavouriteIntent.DeleteWine -> deleteWine(i.wine)
                    }


                }
        }
    }

    private suspend fun getAllWines() {

        _state.value = FavouriteState.ShowProgress

        try {

            withContext(Dispatchers.IO) {
                _state.value = repository.getAllWines()

            }
        } finally {
            _state.value = FavouriteState.HideProgress
        }

    }

    private suspend fun addWine(wine: Wine) {

        _state.value = FavouriteState.ShowProgress

        try {

            withContext(Dispatchers.IO){

                _state.value = repository.addWine(wine)

            }

        } finally {
            _state.value = FavouriteState.HideProgress
        }

    }

    private suspend fun deleteWine(wine: Wine) {

        _state.value = FavouriteState.ShowProgress

        try {

            withContext(Dispatchers.IO) {
                _state.value = repository.deleteWine(wine)
            }
        } finally {
            _state.value = FavouriteState.HideProgress
        }

    }
}