package com.mohamedmabrouk.letsplant.ui.discover.plants

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedmabrouk.letsplant.data.Plant
import com.mohamedmabrouk.letsplant.data.source.PlantsDataSource
import com.mohamedmabrouk.letsplant.data.source.remote.PlantsRemoteDataSource
import javax.inject.Inject

class PlantsListViewModel @Inject constructor(application : Application) : AndroidViewModel(application) {

    val repository: PlantsRepository = PlantsRepository(PlantsRemoteDataSource(application))

    private val _items = MutableLiveData<List<Plant>>().apply { value = emptyList() }
    val items: LiveData<List<Plant>>
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


    fun getPlants(plantType: Int, languageCode: String){
        // todo: check on plant type
        _dataLoading.value = true
        _empty.value = false
        _error.value = null
        _items.value = null

        repository.getPlants(object: PlantsDataSource.LoadPlantsCallback{
            override fun onPlantsLoaded(plants: List<Plant>?) {
                _dataLoading.value = false
                _empty.value = false
                _error.value = null
                _items.value = plants
            }

            override fun onDataNotAvailable() {
                _dataLoading.value = false
                _error.value = null
                _empty.value = true
            }

            override fun onError(error: String) {
                _dataLoading.value = false
                _empty.value = false
                _error.value = error
            }
        }, plantType, languageCode)
    }

}