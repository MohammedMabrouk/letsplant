package com.mohamedmabrouk.letsplant.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.mohamedmabrouk.letsplant.data.DevicePreferences
import javax.inject.Inject

class AnalyticsManager @Inject constructor(
    val context: Context,
    val devicePreferences: DevicePreferences
) {
    private var firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun updateUserProperties() {
        val userId = devicePreferences.getUserId().toString()
//        val fullName =
//            appPreferencesManager.getValue(AppPreferenceConstants.USER_FULL_NAME, "") ?: ""
//        val mobileNumber =
//            appPreferencesManager.getValue(AppPreferenceConstants.USER_MOBILE_NUMBER, "") ?: ""
        // Firebase
        firebaseAnalytics.setUserId(userId)
//        setUserProperty(FirebaseKeys.USER_ID, userId)
//        setUserProperty(FirebaseKeys.USER_NAME, fullName)
//        setUserProperty(FirebaseKeys.USER_PHONE_NUMBER, mobileNumber)
//        setUserProperty(FirebaseKeys.DEVICE_ID, devicePreferences.getDeviceId())
//        setUserProperty(FirebaseKeys.DEVICE_UUID, devicePreferences.getDeviceUuid())
//        setUserProperty(FirebaseKeys.STORE, BuildConfig.STORE)

        // Huawei
//        huaweiAnalytics?.setAnalyticsEnabled(true)
//        huaweiAnalytics?.setUserId(userId)
//        huaweiAnalytics?.setPushToken(devicePreferences.getDeviceHcmToken())
//        huaweiAnalytics?.setUserProfile(FirebaseKeys.USER_NAME, fullName)
//        huaweiAnalytics?.setUserProfile(FirebaseKeys.USER_PHONE_NUMBER, mobileNumber)
//        huaweiAnalytics?.setUserProfile(FirebaseKeys.DEVICE_ID, devicePreferences.getDeviceId())
//        huaweiAnalytics?.setUserProfile(FirebaseKeys.DEVICE_UUID, devicePreferences.getDeviceUuid())
//        huaweiAnalytics?.setUserProfile(FirebaseKeys.STORE, BuildConfig.STORE)
    }

    fun logEvent(eventName: String, bundle: Bundle) {
//        AppLogger.d(TAG, "logging event %s: %s", eventName, bundle.toString())
        firebaseAnalytics.logEvent(eventName, bundle)
//        huaweiAnalytics?.onEvent(eventName, bundle)
    }
}