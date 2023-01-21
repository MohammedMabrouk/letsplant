package com.mohamedmabrouk.letsplant.ui.myPlants

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedmabrouk.letsplant.data.UserPlant
import com.mohamedmabrouk.letsplant.data.source.local.DbUtils
import com.mohamedmabrouk.letsplant.data.source.local.getDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyPlantsViewModel @Inject constructor(val application: Application) : ViewModel() {
    private val database = getDatabase(application)

    private val _items = MutableLiveData<MutableList<UserPlant>>()
    val items: LiveData<MutableList<UserPlant>>
        get() = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean>
        get() = _empty

    fun getUserPlants() {
        viewModelScope.launch {
            _dataLoading.value = true
            val plants = database.userPlantsDao.getAllUserPlants()
            if (plants.isNotEmpty()) {
                _dataLoading.value = false
                _empty.value = false
            } else {
                _dataLoading.value = false
                _empty.value = true
            }
            _items.value = plants
        }
    }


    fun addNewPlant(plant: UserPlant) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    database.userPlantsDao.insertPlant(plant)
                }
                // update list
                DbUtils.refreshReminders(database)
                getUserPlants()
            } catch (e: Exception) {
//                CrashlyticsManager.logException()
            }
        }
    }

    fun deletePlant(plant: UserPlant) {
        viewModelScope.launch {
            withContext(Job() + Dispatchers.IO) {
                database.userPlantsDao.deletePlant(plant)
                database.remindersDao.deletePlantReminders(plant.id)
            }
            //
        }
    }
    // todo: remove debug builds
}