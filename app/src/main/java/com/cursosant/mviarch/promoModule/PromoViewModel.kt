package com.cursosant.mviarch.promoModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursosant.mviarch.loginModule.intent.LoginIntent
import com.cursosant.mviarch.loginModule.model.LoginState
import com.cursosant.mviarch.promoModule.intent.PromoIntent
import com.cursosant.mviarch.promoModule.model.PromoRepository
import com.cursosant.mviarch.promoModule.model.PromoState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class PromoViewModel(private val repository: PromoRepository): ViewModel() {

    private val _state = MutableStateFlow<PromoState>(PromoState.Init)
    val state: StateFlow<PromoState> = _state

    val channel = Channel<PromoIntent>(Channel.UNLIMITED)

    init {
        setupIntent()
    }

    private fun setupIntent() {

        viewModelScope.launch {
            channel.consumeAsFlow()
                .collect{ i ->
                    when(i) {

                        is PromoIntent.RequestPromo -> requestPromos()
                    }
                }
        }
    }

    private fun requestPromos() {
        _state.value = repository.getPromos()
    }
}