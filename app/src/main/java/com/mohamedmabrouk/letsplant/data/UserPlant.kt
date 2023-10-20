package com.mohamedmabrouk.letsplant.data

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Keep
@Parcelize
@Entity(tableName = "user_plant")
data class UserPlant(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0,
    var name: String? = "",
    var wateringRepeatCount: Int? = 0,
    var lastWateringDate: Date?,
    var nextWateringDate: Date?,
    var fertilizeRepeatCount: Int? = 0,
    var lastFertilizeDate: Date?,
    var nextFertilizeDate: Date?,
) : Parcelable