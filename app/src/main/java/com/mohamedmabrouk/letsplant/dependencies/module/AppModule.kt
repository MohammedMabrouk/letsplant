package com.mohamedmabrouk.letsplant.dependencies.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.mohamedmabrouk.letsplant.util.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideAppPreferencesManager(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.APP_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

//    @Provides
//    @Singleton
//    fun provideFirebaseConfigManager(): FirebaseRemoteConfigManager {
//        return FirebaseRemoteConfigManager()
//    }
}