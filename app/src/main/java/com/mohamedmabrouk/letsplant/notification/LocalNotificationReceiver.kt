package com.mohamedmabrouk.letsplant.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.Reminder
import com.mohamedmabrouk.letsplant.data.source.local.ReminderType
import com.mohamedmabrouk.letsplant.ui.welcome.WelcomeActivity

class LocalNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent.getParcelableExtra<Reminder>(REMINDER_INTENT)?.let { reminder ->
            showNotification(context, reminder)
        }
    }

    private fun showNotification(context: Context, reminder: Reminder) {
        val notificationTitle = when (reminder.reminderType) {
            ReminderType.WATERING -> context.getString(R.string.notification_title_water)
            ReminderType.FERTILIZE -> context.getString(R.string.notification_title_fertilize)
            null -> context.getString(R.string.notification_title_water)
        }

        val notificationText = when (reminder.reminderType) {
            ReminderType.WATERING -> context.getString(
                R.string.notification_text_water,
                reminder.plantName
            )

            ReminderType.FERTILIZE -> context.getString(
                R.string.notification_text_fertilize,
                reminder.plantName
            )

            null -> context.getString(R.string.notification_text_water, reminder.plantName)
        }

        val notificationIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, WelcomeActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.green_tea)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setContentIntent(notificationIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(reminder.id?.toInt() ?: NOTIFICATION_ID, notification)
    }

    companion object {
        const val NOTIFICATION_ID = 1000
        const val CHANNEL_ID = "lets_plant_app_channel"
        const val REMINDER_INTENT = "reminderExtra"
    }
}

//todo: open app when click on notifications