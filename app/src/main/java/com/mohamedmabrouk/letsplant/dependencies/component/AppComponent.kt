package com.mohamedmabrouk.letsplant.dependencies.component

import android.app.Application
import com.mohamedmabrouk.letsplant.application.MyApplication
import com.mohamedmabrouk.letsplant.dependencies.buider.ActivityBuilder
import com.mohamedmabrouk.letsplant.dependencies.buider.ServiceBuilder
import com.mohamedmabrouk.letsplant.dependencies.module.AppModule
import com.mohamedmabrouk.letsplant.dependencies.module.BroadcastModule
import com.mohamedmabrouk.letsplant.dependencies.module.NetworkModule
import com.mohamedmabrouk.letsplant.dependencies.module.WorkerModule
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