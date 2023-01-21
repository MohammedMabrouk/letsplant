package com.mohamedmabrouk.letsplant.util

import android.content.Context
import android.preference.PreferenceManager
import com.yariksoffice.lingver.Lingver
import java.util.*
import javax.inject.Inject


class LocaleHelper @Inject constructor(val context: Context) {

    fun getSelectedLanguageId(): Int {
        return getSelectedLanguageId(getPersistedData(Locale.getDefault().language) ?: "")
    }

    fun getSelectedLanguageId(selectedLanguage: String): Int {
        return when (selectedLanguage) {
            LANGUAGE_ENGLISH -> Constants.AppLanguageId.ENGLISH.languageId
            else -> Constants.AppLanguageId.ARABIC.languageId
        }
    }

    fun isLangSelected(): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(IS_LANGUAGE_SELECTED, false)
    }

    @Suppress("SameParameterValue")
    private fun setIsLangSelected(isSelected: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putBoolean(IS_LANGUAGE_SELECTED, isSelected)
        editor.apply()
    }

    fun getSelectedLanguageName(): String {
        return when (getPersistedData(Locale.getDefault().language)) {
            LANGUAGE_ARABIC -> LANGUAGE_ARABIC
            else -> LANGUAGE_ENGLISH
        }
    }

    fun isArabicSelected(): Boolean {
        return getSelectedLanguageName() != LANGUAGE_ENGLISH
    }

    private fun getPersistedData(defaultLanguage: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun persist(language: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    fun setUsersLocale(language: String?) {
        setIsLangSelected(true)
        setLocale(context, language)
    }

    private fun setLocale(context: Context, language: String?) {
        persist(language)
//        val firebaseRemoteConfigManager = FirebaseRemoteConfigManager()
//        firebaseRemoteConfigManager.reset()
        language?.let { Lingver.getInstance().setLocale(context = context, language = it) }
    }

    companion object {
        const val LANGUAGE_ENGLISH = "en"
        const val LANGUAGE_ARABIC = "ar"

        private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
        private const val IS_LANGUAGE_SELECTED = "IS_LANGUAGE_SELECTED"

        fun getCountryCode(lang: String?) = if (lang == LANGUAGE_ARABIC) "EG" else "US"
    }
}