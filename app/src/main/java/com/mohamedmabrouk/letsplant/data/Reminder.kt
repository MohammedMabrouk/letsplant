package com.mohamedmabrouk.letsplant.data

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mohamedmabrouk.letsplant.data.source.local.ReminderStatus
import com.mohamedmabrouk.letsplant.data.source.local.ReminderType
import kotlinx.android.parcel.Parcelize
import java.util.*


@Keep
@Parcelize
@Entity(tableName = "reminder", indices = [Index(value = ["plantId", "date", "reminderType"], unique = true)])
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0,
    var plantId: Long? = 0,
    var plantName: String? = "",
    var date: Date?,
    var reminderType: ReminderType?,
    var repeatCount: Int?,
    var status: ReminderStatus?
) : Parcelable