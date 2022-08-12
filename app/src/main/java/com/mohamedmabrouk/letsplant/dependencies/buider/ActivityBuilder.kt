package com.mohamedmabrouk.letsplant.dependencies.buider

import com.mohamedmabrouk.letsplant.ui.home.HomeActivity
import com.mohamedmabrouk.letsplant.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindsSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun bindsHomeActivity(): HomeActivity
}