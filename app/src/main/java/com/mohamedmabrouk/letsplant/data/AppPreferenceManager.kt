package com.mohamedmabrouk.letsplant.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DevicePreferences @Inject constructor(private val context: Context){

    private val sharedPref: SharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)

    fun saveValue(key: String, value: String) {
        with (sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getValue(key: String): String?{
        return sharedPref.getString(key, null)
    }

    fun saveDeviceToken(token: String){
        saveValue(DEVICE_TOKEN, token)
    }

    fun getDeviceToken(): String?{
        return getValue(DEVICE_TOKEN)
    }

    companion object {
        private const val APP_VERSION = "APP_VERSION"
        private const val DEVICE_ID = "DEVICE_ID"
        private const val DEVICE_UUID = "DEVICE_UUID"
        private const val DEVICE_TOKEN = "DEVICE_FCM_TOKEN"
        private const val USER_NAME = "USER_NAME"
        private const val USER_ID = "USER_ID"
    }
}