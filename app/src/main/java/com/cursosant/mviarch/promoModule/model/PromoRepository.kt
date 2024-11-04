package com.cursosant.mviarch.promoModule.model

import com.cursosant.mviarch.R
import com.cursosant.mviarch.commonModule.utils.Constants


class PromoRepository(private val db: Database) {



        fun getPromos(): PromoState {

            val result = db.getPromos()

            return if (result.isNotEmpty()){
                PromoState.RequestPromoSuccess(result)
            } else {
                PromoState.Fail(Constants.EC_REQUEST, R.string.promo_request_fail)
            }
        }

}