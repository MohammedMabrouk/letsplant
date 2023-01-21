package com.mohamedmabrouk.letsplant.application

import android.app.Application
import android.os.Build
import androidx.work.*
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.firebase.FirebaseApp
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsInstance
import com.huawei.hms.analytics.HiAnalyticsTools
import com.huawei.hms.analytics.type.ReportPolicy
import com.mohamedmabrouk.letsplant.di.component.AppComponent
import com.mohamedmabrouk.letsplant.di.component.DaggerAppComponent
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import com.mohamedmabrouk.letsplant.workManager.RemindersWorker
import com.yariksoffice.lingver.Lingver
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(){

    @Inject
    lateinit var localeHelper: LocaleHelper

    val applicationScope = CoroutineScope(Dispatchers.Default)


    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
        instance = this
        delayedInit()
        initialHuaweiAnalytics()
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
    }


    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RemindersWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RemindersWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    private fun initialHuaweiAnalytics() {
        // Initialize the Analytics SDK by invoking the getInstance interface
        // Enable Analytics Kit Log
        HiAnalyticsTools.enableLog()
        huaweiAnalytics = HiAnalytics.getInstance(this)

        // Application Startup Report Policy.
        val launchAppPolicy = ReportPolicy.ON_APP_LAUNCH_POLICY
        // Report an event upon app switching to the background.
        val moveBackgroundPolicy = ReportPolicy.ON_MOVE_BACKGROUND_POLICY
        // Report an event at the specified interval.
        val scheduledTimePolicy = ReportPolicy.ON_SCHEDULED_TIME_POLICY
        // Set the periodic report interval to 600 seconds.
        scheduledTimePolicy.threshold = 600
        val reportPolicies: MutableSet<ReportPolicy> = HashSet()
        // Add Escalation Policy
        reportPolicies.add(launchAppPolicy)
        reportPolicies.add(moveBackgroundPolicy)
        reportPolicies.add(scheduledTimePolicy)
        // Reporting policy settings take effect.
        huaweiAnalytics.setReportPolicies(reportPolicies)
    }

    companion object {
        private lateinit var instance: MyApplication
        private var googleAnalytics: GoogleAnalytics? = null
        private lateinit var huaweiAnalytics: HiAnalyticsInstance
        fun getInstance(): MyApplication {
            return instance
        }
    }

}