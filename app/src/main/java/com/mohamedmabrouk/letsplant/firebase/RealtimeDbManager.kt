package com.mohamedmabrouk.letsplant.firebase

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class RealtimeDbManager @Inject constructor(val context: Context) {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun getDbReference(path: String): DatabaseReference {
        return database.getReference(path)
    }
}