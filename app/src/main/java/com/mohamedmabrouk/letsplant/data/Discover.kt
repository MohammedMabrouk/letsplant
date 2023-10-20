package com.mohamedmabrouk.letsplant.data

import androidx.annotation.Keep
import com.mohamedmabrouk.letsplant.util.Constants
@Keep
data class Discover(val img: Int, val text: String, val itemType: Constants.DiscoverItem)
