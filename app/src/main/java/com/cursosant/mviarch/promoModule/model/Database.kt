package com.cursosant.mviarch.promoModule.model

import com.cursosant.mviarch.commonModule.dataAccess.local.getAllPromos

class Database {

    fun getPromos() = getAllPromos()
}