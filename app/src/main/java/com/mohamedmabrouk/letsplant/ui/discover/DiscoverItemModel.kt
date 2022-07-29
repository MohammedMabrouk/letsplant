package com.mohamedmabrouk.letsplant.ui.discover

import androidx.annotation.Keep
import com.mohamedmabrouk.letsplant.util.Constants
@Keep
data class DiscoverItemModel(val img: Int, val text: String, val itemType: Constants.DiscoverItem)
