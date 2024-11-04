package com.cursosant.mviarch.homeModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cursosant.mviarch.homeModule.model.HomeRepository

class HomeViewModelfactory(private val repository: HomeRepository): ViewModelProvider.Factory {


    override fun <T: ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){

            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        } else {

            throw IllegalArgumentException("Clase de viewmodel desconocida")
        }
    }
}