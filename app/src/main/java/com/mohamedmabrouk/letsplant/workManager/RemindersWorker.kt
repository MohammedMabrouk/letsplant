package com.mohamedmabrouk.letsplant.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohamedmabrouk.letsplant.data.source.local.DbUtils
import com.mohamedmabrouk.letsplant.data.source.local.getDatabase

class RemindersWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "REMINDERS_WORKER"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)

        return try {
            DbUtils.refreshReminders(database)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}