package com.mohamedmabrouk.letsplant.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.google.firebase.FirebaseApp
import com.mohamedmabrouk.letsplant.BuildConfig
import com.mohamedmabrouk.letsplant.data.source.local.LetsPlantDatabase
import com.mohamedmabrouk.letsplant.notification.RemindersNotificationHandler
import com.mohamedmabrouk.letsplant.reminders.RemindersWorker
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var localeHelper: LocaleHelper

    @Inject
    lateinit var remindersNotificationHandler: RemindersNotificationHandler

    @Inject
    lateinit var remindersWorkerFactory: RemindersWorkerFactory

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        instance = this

//        FirebaseMessaging.getInstance().subscribeToTopic(BuildConfig.FIREBASE_TOPIC)
//        HmsMessaging.getInstance(this).subscribe(BuildConfig.FIREBASE_TOPIC)
//        AppLogger.setupLogs()
//        initializeSSLContext(this)
//        initializeFirebase()
//        initializeAppsFlyer()
        val language = localeHelper.getSelectedLanguageName()
        Lingver.init(this, language)
        /*TODO this snippet for testing purpose of UTM flags and later it can be removed */
        /*val intent = Intent("com.android.vending.INSTALL_REFERRER")
        intent.setPackage(this.packageName)
        intent.putExtra("referrer", "utm_source=test_source&utm_medium=test_medium&utm_term=test_term&utm_content=test_content&utm_campaign=test_name")
        this.sendBroadcast(intent)*/

        remindersNotificationHandler.createNotificationChannel()
    }

    companion object {
        private lateinit var instance: MyApplication

        fun getInstance(): MyApplication {
            return instance
        }
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(remindersWorkerFactory)
            .build()

}

class RemindersWorkerFactory @Inject constructor(
    private val appDatabase: LetsPlantDatabase,
    private val todayDate: Date,
    private val remindersNotificationHandler: RemindersNotificationHandler
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker =
        RemindersWorker(
            appContext,
            workerParameters,
            appDatabase,
            todayDate,
            remindersNotificationHandler
        )
}