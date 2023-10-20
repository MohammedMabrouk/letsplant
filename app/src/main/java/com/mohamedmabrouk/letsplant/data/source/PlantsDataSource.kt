package com.mohamedmabrouk.letsplant.data.source

import com.mohamedmabrouk.letsplant.data.Plant

interface PlantsDataSource {

    interface LoadPlantsCallback {

        fun onPlantsLoaded(plants: List<Plant>?)

        fun onDataNotAvailable()

        fun onError(error: String)
    }

    fun getPlants(callback: LoadPlantsCallback, plantsType: Int, languageCode: String)
}