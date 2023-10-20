package com.mohamedmabrouk.letsplant.util

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mohamedmabrouk.letsplant.data.DevicePreferences
import java.util.*
import javax.inject.Inject

class CrashlyticsManager @Inject constructor(private val devicePreferences: DevicePreferences) {

    fun initializeCrashlyticsUser() {
//        tagUser(
//            appPreferencesManager.getValue(AppPreferenceConstants.USER_NIC, "") ?: "",
//            appPreferencesManager.getValue(AppPreferenceConstants.USER_FULL_NAME, "") ?: "",
//            appPreferencesManager.getValue(AppPreferenceConstants.USER_EMAIL, "") ?: ""
//        )
    }

    private fun tagUser(userId: String, name: String, email: String) {
        // Operations on FirebaseCrashlytics.
        crashlytics.setUserId(userId)
        crashlytics.setCustomKey("name", name)
        crashlytics.setCustomKey("email", email)
    }

    fun tagUserNic(national_id: Long) {
        crashlytics.setUserId(national_id.toString())
    }

    fun logException(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    fun logString(key: String, value: String) {
        crashlytics.setCustomKey(key, value)
    }


    fun log(error: Throwable) {
        // because we can't search using the stack trace message
        logForNextException("message", error.message ?: "")
        logException(error)
    }

    /**
     * Executes the passes Runnable, and catches any exception that is thrown by it only in
     * production, but allowing it to throw in developer and QA builds. This should be used to
     * prevent a crash in production upon encountering unexpected but *recoverable* exceptions,
     * that cannot be guarded against in the module (e.g. invalid data or state provided from
     * external modules).
     *
     * @param exceptionRunnable The runnable to execute and catch exceptions from.
     * @return true if an exception was caught while executing the Runnable block, and false if not.
     */
    fun logInProd(exceptionRunnable: Runnable): Boolean {
//        if (IS_PROD_BUILD) {
//            try {
//                exceptionRunnable.run()
//            } catch (exception: Exception) {
//                log(exception)
//                return true
//            }
//
//        } else {
//            exceptionRunnable.run()
//        }
        return false
    }

    fun logForNextException(key: String, value: String) {
        logString(key, value)
    }

    companion object {
        private val crashlytics = FirebaseCrashlytics.getInstance()
        fun logException(throwable: Throwable, userId: String? = null) {
            userId?.let { crashlytics.setUserId(it) }
            crashlytics.recordException(throwable)
        }

        fun logString(log: String, vararg args: Any) {
            crashlytics.log(String.format(Locale.ENGLISH, log, *args))
        }
    }
}