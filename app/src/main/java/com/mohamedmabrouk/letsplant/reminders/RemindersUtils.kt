package com.mohamedmabrouk.letsplant.reminders

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mohamedmabrouk.letsplant.util.Constants.REMINDERS_WORKER_DEFAULT_REQUEST_CODE
import com.mohamedmabrouk.letsplant.util.flag
import java.util.Calendar
import javax.inject.Inject

class RemindersUtils @Inject constructor(private val context: Context) {

    fun scheduleRefreshReminders() {
        Log.d("www", "scheduleRefreshReminders: at 12 am")
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23) // fire at 12AM midnight
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 0)

        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, RefreshRemindersReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(
                context,
                REMINDERS_WORKER_DEFAULT_REQUEST_CODE,
                intent,
                flag
            )
        }

        //cancel all previous alarm
        alarmManager.cancel(alarmIntent)
        alarmManager.set(AlarmManager.RTC_WAKEUP, 0, alarmIntent)

//        AppLogger.d(TAG, "Alarm scheduleRawFile ${calendar.time}")
    }
}