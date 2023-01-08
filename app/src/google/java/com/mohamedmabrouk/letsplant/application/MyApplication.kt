package com.mohamedmabrouk.letsplant.application

import com.google.firebase.FirebaseApp
import com.mohamedmabrouk.letsplant.dependencies.component.AppComponent
import com.mohamedmabrouk.letsplant.dependencies.component.DaggerAppComponent
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import com.yariksoffice.lingver.Lingver
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class MyApplication : DaggerApplication(){

    @Inject
    lateinit var localeHelper: LocaleHelper


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent: AppComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
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