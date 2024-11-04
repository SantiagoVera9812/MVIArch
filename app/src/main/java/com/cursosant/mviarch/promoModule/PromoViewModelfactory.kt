package com.cursosant.mviarch.promoModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cursosant.mviarch.homeModule.model.HomeRepository
import com.cursosant.mviarch.loginModule.model.LoginRepository
import com.cursosant.mviarch.promoModule.model.PromoRepository

class PromoViewModelfactory(private val repository: PromoRepository): ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(PromoViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return PromoViewModel(repository) as T
        } else {

            throw IllegalArgumentException("Clase de viewmodel desconocida")
        }
    }

}