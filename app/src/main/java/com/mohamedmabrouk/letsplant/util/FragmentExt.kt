package com.mohamedmabrouk.letsplant.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.mohamedmabrouk.letsplant.R

fun Fragment.showLoadingIndicator(fragment: Fragment){
    (fragment.view?.findViewById(R.id.pb_loading) as ProgressBar).visibility = View.VISIBLE
    fragment.activity?.window?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Fragment.hideLoadingIndicator(fragment: Fragment){
    (fragment.view?.findViewById(R.id.pb_loading) as ProgressBar).visibility = View.GONE
    fragment.activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

//fun Activity.hideKeyboard() {
//    hideKeyboard(currentFocus ?: View(this))
//}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}