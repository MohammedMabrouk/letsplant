package com.mohamedmabrouk.letsplant.util

import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.mohamedmabrouk.letsplant.R

fun Fragment.showLoadingIndicator(fragment: Fragment){
    (fragment.view?.findViewById(R.id.pb_loading) as ProgressBar).visibility = View.VISIBLE
}

fun Fragment.hideLoadingIndicator(fragment: Fragment){
    (fragment.view?.findViewById(R.id.pb_loading) as ProgressBar).visibility = View.GONE
}