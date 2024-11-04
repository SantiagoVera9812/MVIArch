package com.cursosant.mviarch.commonModule.dataAccess.retrofit

import com.cursosant.mviarch.commonModule.utils.Constants
import com.cursosant.mviarch.commonModule.entities.Wine
import retrofit2.http.GET

/****
 * Project: Wines
 * From: com.cursosant.wines
 * Created by Alain Nicolás Tello on 06/02/24 at 20:23
 * All rights reserved 2024.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/
interface WineService {
    // https://sampleapis.com/api-list/wines
    @GET(Constants.PATH_WINES)
    suspend fun getRedWines() : List<Wine>
}