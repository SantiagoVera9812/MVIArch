package com.cursosant.mviarch.updateModule.intent

import com.cursosant.mviarch.commonModule.entities.Wine
import com.cursosant.mviarch.updateModule.view.UpdateDialogFragment

sealed class UpdateIntent{

    data class RequestwINE(val id: Double): UpdateIntent()
    data class UpdateWine(val wine: Wine): UpdateIntent()
}