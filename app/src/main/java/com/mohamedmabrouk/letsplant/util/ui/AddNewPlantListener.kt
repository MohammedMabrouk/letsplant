package com.mohamedmabrouk.letsplant.util.ui

import com.mohamedmabrouk.letsplant.data.UserPlant

interface AddNewPlantListener {
    fun onNewPlantAdded(plant: UserPlant)
}