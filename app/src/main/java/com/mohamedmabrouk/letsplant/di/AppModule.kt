package com.mohamedmabrouk.letsplant.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mohamedmabrouk.letsplant.application.MyApplication
import com.mohamedmabrouk.letsplant.data.source.local.LetsPlantDatabase
import com.mohamedmabrouk.letsplant.data.source.local.getDatabase
import com.mohamedmabrouk.letsplant.notification.RemindersNotificationHandler
import com.mohamedmabrouk.letsplant.util.DateTimeUtils
import com.mohamedmabrouk.letsplant.util.NetworkState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Date
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApplication {
        return app as MyApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: MyApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(client: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    private val READ_TIMEOUT = 30
//    private val WRITE_TIMEOUT = 30
//    private val CONNECTION_TIMEOUT = 10
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient {
//        val okHttpClientBuilder = OkHttpClient().newBuilder()
//        okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
//        okHttpClientBuilder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
//        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
//
//        return okHttpClientBuilder.build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideApi(retrofit: Retrofit): ProductAPIs {
//        return retrofit.create(ProductAPIs::class.java)
//    }

    @Provides
    @Singleton
    fun provideNetworkState(context: Context): NetworkState {
        return NetworkState(context)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): LetsPlantDatabase {
        return getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideReminderNotificationHelper(context: Context): RemindersNotificationHandler {
        return RemindersNotificationHandler(context)
    }

    @Provides
    @Singleton
    fun provideTodayDate(): Date {
        return DateTimeUtils.getCurrentDate()
    }

//
//    @Provides
//    @Singleton
//    fun provideProductsRepository(
//        networkState: NetworkState,
//        productAPIs: ProductAPIs
//    ): ProductsRepository {
//        return DefaultProductsRepository(
//            ProductsRemoteDataSource(
//                networkState,
//                productAPIs
//            )
//        )
//    }
//
//    @Provides
//    @Singleton
//    fun provideErrorResolver(context: Context): ErrorResolver {
//        return ErrorResolver(context)
//    }
}