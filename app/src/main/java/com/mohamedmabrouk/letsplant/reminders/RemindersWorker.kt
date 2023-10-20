package com.mohamedmabrouk.letsplant.reminders

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mohamedmabrouk.letsplant.data.source.local.DbUtils
import com.mohamedmabrouk.letsplant.data.source.local.LetsPlantDatabase
import com.mohamedmabrouk.letsplant.notification.RemindersNotificationHandler
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

@HiltWorker
class RemindersWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val appDatabase: LetsPlantDatabase,
    private val todayDate: Date,
    private val remindersNotificationHandler: RemindersNotificationHandler
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        Log.d("www", "doWork: --->")

        //todo: add logic for notifications
        return try {
            withContext(Dispatchers.IO) {
                DbUtils.refreshReminders(appDatabase)
                val todayReminders = appDatabase.remindersDao.getTodayReminders(todayDate)
                todayReminders.map {
                    remindersNotificationHandler.scheduleNotification(it)
                }
                Log.d("www", "doWork: success, we have ${todayReminders.size} reminders")
                Result.success()
            }
        } catch (e: Exception) {
            Log.d("www", "doWork: failure $e")

            Result.retry()
        }
    }

    companion object {
        fun enqueueWork(context: Context) {
            val refreshRemindersWorkRequest: OneTimeWorkRequest =
                OneTimeWorkRequestBuilder<RemindersWorker>().build()

            WorkManager.getInstance(context).enqueue(refreshRemindersWorkRequest)
        }
    }
}

//todo: check if not necessary