package com.mohamedmabrouk.letsplant.dependencies.buider

import com.mohamedmabrouk.letsplant.messagingService.MessagingService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuilder {
    @ContributesAndroidInjector
    abstract fun bindsFireMessagingService(): MessagingService

//    @ContributesAndroidInjector
//    abstract fun bindsHmsMessageService(): HmsMessageService
}