package com.mohamedmabrouk.letsplant.util

import java.util.*

object NumbersUtility {
    fun getFormattedInteger(number: String, locale: Locale): String {
        return if (locale.language == "ar")
            number.replace("1", "١")
                .replace("2", "٢")
                .replace("3", "٣")
                .replace("4", "٤")
                .replace("5", "٥")
                .replace("6", "٦")
                .replace("7", "٧")
                .replace("8", "٨")
                .replace("9", "٩")
                .replace("0", "٠")
                .replace(".", ".")
                .replace("٬", ",")
        else number
    }
}