package com.cursosant.mviarch.updateModule.model

import com.cursosant.mviarch.WineApplication
import com.cursosant.mviarch.commonModule.dataAccess.room.WineDao
import com.cursosant.mviarch.commonModule.entities.Wine

class RoomDatabase {

    private val dao: WineDao by lazy { WineApplication.database.wineDao() }

    fun getWineById(id: Double) = dao.getWineById(id)

    fun updateWine(wine: Wine) = dao.updateWine(wine)
}