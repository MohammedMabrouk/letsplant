package com.mohamedmabrouk.letsplant.di.component

import android.app.Application
import com.mohamedmabrouk.letsplant.application.MyApplication
import com.mohamedmabrouk.letsplant.di.buider.ActivityBuilder
import com.mohamedmabrouk.letsplant.di.buider.ServiceBuilder
import com.mohamedmabrouk.letsplant.di.module.AppModule
import com.mohamedmabrouk.letsplant.di.module.BroadcastModule
import com.mohamedmabrouk.letsplant.di.module.NetworkModule
import com.mohamedmabrouk.letsplant.di.module.WorkerModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AppModule::class),
    (NetworkModule::class),
    (AndroidSupportInjectionModule::class),
    (ServiceBuilder::class),
    (ActivityBuilder::class),
    (BroadcastModule::class),
    (WorkerModule::class)
])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(myApplication: MyApplication)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}