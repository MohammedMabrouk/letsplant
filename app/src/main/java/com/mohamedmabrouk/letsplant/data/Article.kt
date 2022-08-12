package com.mohamedmabrouk.letsplant.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Article(
    var id: Long? = 0,
    val imgUrl: String? ="",
    val title: String? ="",
    val details: String? ="") : Parcelable
