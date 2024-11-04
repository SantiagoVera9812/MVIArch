package com.cursosant.mviarch.updateModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursosant.mviarch.commonModule.entities.Wine

import com.cursosant.mviarch.favouriteModule.intent.FavouriteIntent
import com.cursosant.mviarch.favouriteModule.model.FavouriteState
import com.cursosant.mviarch.updateModule.intent.UpdateIntent
import com.cursosant.mviarch.updateModule.model.UpdateRepository
import com.cursosant.mviarch.updateModule.model.UpdateState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateViewModel(private val repository: UpdateRepository): ViewModel() {

    private val _state = MutableStateFlow<UpdateState>(UpdateState.Init)
    val state: StateFlow<UpdateState> = _state

    val channel = Channel<UpdateIntent> { Channel.UNLIMITED}

    init {
        setUpIntent()
    }

    private fun setUpIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow()
                .collect{ i ->
                    when(i) {

                        is UpdateIntent.UpdateWine -> updateWine(i.wine)
                        is UpdateIntent.RequestwINE -> requestWine(i.id)

                    }


                }
        }
    }

    private suspend fun requestWine(id: Double) {

        _state.value = UpdateState.ShowProgress

        try {
            withContext(Dispatchers.IO){
                _state.value = repository.requestWine(id)
            }
        } finally {

            _state.value = UpdateState.HideProgress
        }

    }

    private suspend fun updateWine(wine: Wine) {

        _state.value = UpdateState.ShowProgress

        try {
            withContext(Dispatchers.IO){
                _state.value = repository.updateWine(wine)
            }
        } finally {

            _state.value = UpdateState.HideProgress
        }

    }


}