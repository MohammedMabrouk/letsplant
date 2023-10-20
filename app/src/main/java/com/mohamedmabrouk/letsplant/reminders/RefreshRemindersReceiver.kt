package com.mohamedmabrouk.letsplant.reminders

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mohamedmabrouk.letsplant.util.Constants.REMINDERS_WORKER_DEFAULT_REQUEST_CODE
import com.mohamedmabrouk.letsplant.util.flag
import java.util.Calendar

class RefreshRemindersReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("www", "RefreshRemindersReceiver - onReceive: triggering work manager and set for next day")
        RemindersWorker.enqueueWork(context)
        scheduleForNextDay(context)
    }

    private fun scheduleForNextDay(context: Context) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23) // fire at 12AM midnight
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 0)

        // time is in past then add one day to calendar
//        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
//        }

        val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, RefreshRemindersReceiver::class.java).let { intent ->
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(context, REMINDERS_WORKER_DEFAULT_REQUEST_CODE, intent, flag)
            } else {
                PendingIntent.getBroadcast(context, REMINDERS_WORKER_DEFAULT_REQUEST_CODE, intent,
                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
            }
        }

        //cancel all previous alarm
        alarmManager.cancel(alarmIntent)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)

//        AppLogger.d("RawDataHandler", "Alarm scheduleRawFile ${calendar.time}")
    }
}