package com.mohamedmabrouk.letsplant.util

import androidx.annotation.Keep

object Constants {
    @Keep
    enum class DiscoverItem {
        INDOOR_PLANTS, OUTDOOR_PLANTS, PLANTS_CARE
    }

    const val PLANTS_TYPE = "PLANTS_TYPE"
    const val ARTICLES_TYPE = "ARTICLES_TYPE"

    const val GOOGLE_HOST = "8.8.8.8"
    const val GOOGLE_PORT = 443

    @Keep
    enum class AppLanguageId(val languageId: Int) {
        ENGLISH(1),
        ARABIC(2)
    }

    // App Preference
    const val APP_PREFERENCES_KEY = "APP_PREFERENCES_KEY"
    const val USER_LANGUAGE = "USER_LANGUAGE"
    const val USER_ID = "USER_ID"
    const val USER_PHONE_NUMBER = "USER_PHONE_NUMBER"
    const val USER_FULL_NAME = "USER_FULL_NAME"
    const val USER_EMAIL = "USER_EMAIL"

    // others
    const val ACTION_NEW_NOTIFICATION = "ACTION_NEW_NOTIFICATION"

}