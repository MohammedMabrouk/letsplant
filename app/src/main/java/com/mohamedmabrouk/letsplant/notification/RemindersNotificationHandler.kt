package com.mohamedmabrouk.letsplant.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mohamedmabrouk.letsplant.data.Reminder
import com.mohamedmabrouk.letsplant.notification.LocalNotificationReceiver.Companion.REMINDER_INTENT
import com.mohamedmabrouk.letsplant.util.Constants.REMINDERS_NOTIFICATION_DEFAULT_REQUEST_CODE
import java.util.Calendar
import java.util.GregorianCalendar
import javax.inject.Inject


class RemindersNotificationHandler @Inject constructor(private val context: Context) {

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Lets Plant Notification Channel"
            val desc = "Used for app notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

            val channel =
                NotificationChannel(LocalNotificationReceiver.CHANNEL_ID, name, importance)
            channel.description = desc
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotification(reminder: Reminder) {
        Log.d("www", "scheduleNotification: ${reminder.plantName} - ${reminder.reminderType}")
        val intent = Intent(context, LocalNotificationReceiver::class.java)
        intent.putExtra(REMINDER_INTENT, reminder)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id?.toInt() ?: REMINDERS_NOTIFICATION_DEFAULT_REQUEST_CODE,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = GregorianCalendar.getInstance().apply {
            // fire at 8 AM
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } catch (exception: SecurityException) {
                FirebaseCrashlytics.getInstance().recordException(exception)
            }
        }

//        AnalyticsHelper.getInstance(context).trackNotificationScheduled(reminder)
    }
}