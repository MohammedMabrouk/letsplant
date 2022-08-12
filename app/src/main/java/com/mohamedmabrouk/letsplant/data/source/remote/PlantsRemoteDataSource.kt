package com.mohamedmabrouk.letsplant.data.source.remote

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mohamedmabrouk.letsplant.data.Plant
import com.mohamedmabrouk.letsplant.data.source.PlantsDataSource
import com.mohamedmabrouk.letsplant.firebase.ErrorCodeMapper
import com.mohamedmabrouk.letsplant.firebase.RealtimeDbManager
import com.mohamedmabrouk.letsplant.util.ConnectivityUtils
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import com.mohamedmabrouk.letsplant.util.ResourceManager
import dagger.Module
import javax.inject.Inject

@Module
class PlantsRemoteDataSource @Inject constructor(val context: Context) : PlantsDataSource {
    private val TAG = "PlantsRemoteDataSource"

    var realtimeDbManager: RealtimeDbManager = RealtimeDbManager(context)
    var errorCodeMapper: ErrorCodeMapper =
        ErrorCodeMapper(ResourceManager(context, LocaleHelper(context)))

    override fun getPlants(callback: PlantsDataSource.LoadPlantsCallback, plantsType: Int, languageCode: String) {
        val plantTypeString = when (plantsType){
            0 -> "Indoor"
            1 -> "Outdoor"
            else -> "Indoor"
        }
        val DB_PATH = "Plants/$plantTypeString/$languageCode"
        Log.v(TAG, "requesting: $DB_PATH")
        if (ConnectivityUtils.isNetworkAvailable(context)) {
            realtimeDbManager.getDbReference(DB_PATH)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val plantsList = ArrayList<Plant>()
                        var plant: Plant?
                        for (plantSnapshot: DataSnapshot in dataSnapshot.children) {
                            plant = plantSnapshot.getValue(Plant::class.java)
                                .apply { this?.id = plantSnapshot.key?.toLong() }

                            if (plant != null)
                                plantsList.add(plant)
                        }

                        if (plantsList.isEmpty())
                            callback.onDataNotAvailable()
                        else
                            callback.onPlantsLoaded(plantsList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback.onError(errorCodeMapper.getFirebaseDataBaseError(error.code))
                    }
                })
        } else {
            callback.onError(errorCodeMapper.getFirebaseDataBaseError(DatabaseError.DISCONNECTED))
        }

    }
}