package com.mohamedmabrouk.letsplant.util

import android.content.Context
import android.content.res.Configuration
import androidx.annotation.StringRes
import java.util.*
import javax.inject.Inject


class ResourceManager @Inject constructor(
    private val context: Context,
    private val localeHelper: LocaleHelper) {

    fun getString(@StringRes id: Int, formatArgs: Any = ""): String {
        return if (formatArgs == "")
            getLocalizedString(id)
        else
            getLocalizedString(id,formatArgs)
    }

    fun getLocalizedString(@StringRes id: Int): String {
        val language = localeHelper.getSelectedLanguageName()
        val locale = Locale(language, LocaleHelper.getCountryCode(language))
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        val context = context.createConfigurationContext(config)
        return context.getString(id)
    }

    fun getLocalizedString(@StringRes id: Int, formatArgs: Any): String {
        val language = localeHelper.getSelectedLanguageName()
        val locale = Locale(language, LocaleHelper.getCountryCode(language))
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        val context = context.createConfigurationContext(config)
        return context.getString(id, formatArgs)
    }

    fun getStringByLanguage(@StringRes id: Int, language: String): String {
        val locale = Locale(language, LocaleHelper.getCountryCode(language))
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        val context = context.createConfigurationContext(config)
        return context.getString(id)
    }
}