package com.mohamedmabrouk.letsplant.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject


class DevicePreferences @Inject constructor(val context: Context){

    private val sharedPref: SharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)

    private fun saveStringValue(key: String, value: String) {
        with (sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    private fun getStringValue(key: String): String?{
        return sharedPref.getString(key, null)
    }

    private fun saveBooleanValue(key: String, value: Boolean) {
        with (sharedPref.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    private fun getBooleanValue(key: String): Boolean{
        return sharedPref.getBoolean(key, false)
    }

    fun saveDeviceToken(token: String){
        saveStringValue(DEVICE_TOKEN, token)
    }

    fun getDeviceToken(): String?{
        return getStringValue(DEVICE_TOKEN)
    }

    fun saveUserId(userId: String){
        saveStringValue(USER_ID, userId)
    }

    fun getUserId(): String?{
        return getStringValue(USER_ID)
    }

    fun saveLoginState(isLoggedIn: Boolean){
        saveBooleanValue(IS_LOGGED_IN, isLoggedIn)
    }

    fun getLoginState(): Boolean{
        return getBooleanValue(IS_LOGGED_IN)
    }

    companion object {
        private const val APP_VERSION = "APP_VERSION"
        private const val DEVICE_ID = "DEVICE_ID"
        private const val DEVICE_UUID = "DEVICE_UUID"
        private const val DEVICE_TOKEN = "DEVICE_FCM_TOKEN"
        private const val USER_NAME = "USER_NAME"
        private const val USER_ID = "USER_ID"

        private const val IS_LOGGED_IN = "IS_LOGGED_IN"
    }
}