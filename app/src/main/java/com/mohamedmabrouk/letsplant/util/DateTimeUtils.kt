package com.mohamedmabrouk.letsplant.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeUtils {
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val REMINDER_DATE_FORMAT = "E MMMM dd, yyyy"

    fun getCurrentDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    fun getDateWithAddedDays(days: Int?): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        if (days != null)
            calendar.add(Calendar.DATE, days)
        return calendar.time
    }

    fun getDateString(date: Date?, format: String, locale: Locale): String {
        return try {
            val dateFormat = SimpleDateFormat(format, locale)
            dateFormat.format(date!!).toString()
        } catch (e: Exception) {
            ""
        }
    }
}