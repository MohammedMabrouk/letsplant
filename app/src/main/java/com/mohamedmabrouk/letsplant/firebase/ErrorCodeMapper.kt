package com.mohamedmabrouk.letsplant.firebase

import androidx.annotation.StringRes
import com.google.firebase.database.DatabaseError
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.util.ResourceManager
import javax.inject.Inject

class ErrorCodeMapper @Inject constructor(val resourceManager: ResourceManager) {
    private val firebaseDataBaseErrorMap = HashMap<Int, Int>()

    init {
        addFirebaseError(DatabaseError.DISCONNECTED, R.string.internet_not_available)
    }

    private fun addFirebaseError(databaseError: Int, @StringRes stringRes: Int) {
        firebaseDataBaseErrorMap[databaseError] = stringRes
    }

    fun getFirebaseDataBaseError(errorCode: Int?) : String {
        val stringId =  firebaseDataBaseErrorMap[errorCode] ?: R.string.request_failure
        return getLocalizedString(stringId)
    }

    private fun getLocalizedString(@StringRes id: Int): String {
        return resourceManager.getLocalizedString(id)
    }
}