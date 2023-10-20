package com.mohamedmabrouk.letsplant.firebase

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import com.mohamedmabrouk.letsplant.data.DevicePreferences
import javax.inject.Inject

class FirebaseManager @Inject constructor(
    val context: Context,
    val devicePreferences: DevicePreferences
) {
    private val TAG = "FB Manager"
    private var firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    //todo: call in splash
    fun getDeviceToken() {
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { result ->
                Log.d("$TAG DEVICE_TOKEN", result)
                devicePreferences.saveDeviceToken(result)
            }
    }

    fun logEvent(eventName: String, bundle: Bundle) {
        Log.d(TAG, "logging event $eventName :  $bundle")
        firebaseAnalytics.logEvent(eventName, bundle)
    }
}