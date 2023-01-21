package com.mohamedmabrouk.letsplant.application

import android.app.Application
import com.google.firebase.FirebaseApp
import com.mohamedmabrouk.letsplant.BuildConfig
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(){

    @Inject
    lateinit var localeHelper: LocaleHelper


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
    }

    companion object {
        private lateinit var instance: MyApplication

        fun getInstance(): MyApplication {
            return instance
        }
    }

}