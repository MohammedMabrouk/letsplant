package com.mohamedmabrouk.letsplant.application

import com.mohamedmabrouk.letsplant.dependencies.component.AppComponent
import com.mohamedmabrouk.letsplant.dependencies.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication : DaggerApplication(){
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent: AppComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

}