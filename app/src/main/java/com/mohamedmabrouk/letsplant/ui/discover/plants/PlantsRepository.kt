package com.mohamedmabrouk.letsplant.ui.discover.plants

import com.mohamedmabrouk.letsplant.data.Plant
import com.mohamedmabrouk.letsplant.data.source.PlantsDataSource
import com.mohamedmabrouk.letsplant.data.source.remote.PlantsRemoteDataSource
import javax.inject.Inject

class PlantsRepository @Inject constructor(val plantsRemoteDataSource: PlantsRemoteDataSource) : PlantsDataSource {
    // todo: check to get data locally or from server
    override fun getPlants(callback: PlantsDataSource.LoadPlantsCallback, plantsType: Int, languageCode: String) {
        plantsRemoteDataSource.getPlants(object : PlantsDataSource.LoadPlantsCallback{
            override fun onPlantsLoaded(plants: List<Plant>?) {
                callback.onPlantsLoaded(plants)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

            override fun onError(error: String) {
                callback.onError(error)
            }
        }, plantsType, languageCode)
    }

}