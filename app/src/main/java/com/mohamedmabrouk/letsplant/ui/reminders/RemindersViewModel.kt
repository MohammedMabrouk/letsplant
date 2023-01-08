package com.mohamedmabrouk.letsplant.ui.reminders

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mohamedmabrouk.letsplant.data.Reminder
import com.mohamedmabrouk.letsplant.data.source.local.ReminderType
import com.mohamedmabrouk.letsplant.data.source.local.getDatabase
import com.mohamedmabrouk.letsplant.util.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class RemindersViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)

    private val _items = MutableLiveData<MutableList<Reminder>>()
    val items: LiveData<MutableList<Reminder>>
        get() = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean>
        get() = _empty


    fun getTodayReminders(todayDate: Date) {
        viewModelScope.launch {
            _dataLoading.value = true
            _empty.value = false
            val reminders = database.remindersDao.getTodayReminders(todayDate)
            if (reminders.isNotEmpty()) {
                _dataLoading.value = false
                _empty.value = false
            } else {
                _dataLoading.value = false
                _empty.value = true
            }
            _items.value = reminders
        }
    }

    fun getUpcomingReminders(todayDate: Date) {
        viewModelScope.launch {
            _dataLoading.value = true
            _empty.value = false
            val reminders = database.remindersDao.getUpcomingReminders(todayDate)
            if (reminders.isNotEmpty()) {
                _dataLoading.value = false
                _empty.value = false
            } else {
                _dataLoading.value = false
                _empty.value = true
            }
            _items.value = reminders
        }
    }

    fun addNewReminder(reminder: Reminder) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    database.remindersDao.insertReminder(reminder)
                }
            } catch (e: Exception) {
//                CrashlyticsManager.logException()
            }
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.remindersDao.deleteReminder(reminder)
            }
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    database.remindersDao.updateReminder(reminder)
                    // update plant dates
                    if (reminder.reminderType == ReminderType.WATERING) {
                        database.userPlantsDao.updatePlantWateringDates(
                            reminder.plantId,
                            DateTimeUtils.getCurrentDate(),
                            DateTimeUtils.getDateWithAddedDays(reminder.repeatCount)
                        )
                    } else if (reminder.reminderType == ReminderType.FERTILIZE) {
                        database.userPlantsDao.updatePlantFertilizeDates(
                            reminder.plantId,
                            DateTimeUtils.getCurrentDate(),
                            DateTimeUtils.getDateWithAddedDays(reminder.repeatCount)
                        )
                    }
                }
            } catch (e: Exception) {
//                CrashlyticsManager.logException()
            }
        }
    }

}