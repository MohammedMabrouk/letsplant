package com.mohamedmabrouk.letsplant.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Plant(
    var id: Long? = 0,
    var imgUrl: String? = "",
    var name: String? = "",
    var overview: String? ="",
    var light: String? ="",
    var watering: String? ="",
    var temprature: String? ="",
    var soil: String? ="",
    var propagation: String? ="") : Parcelable
