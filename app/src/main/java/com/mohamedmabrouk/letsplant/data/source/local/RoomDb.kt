package com.mohamedmabrouk.letsplant.data.source.local

import android.content.Context
import android.util.Log
import androidx.room.*
import com.mohamedmabrouk.letsplant.data.Reminder
import com.mohamedmabrouk.letsplant.data.UserPlant
import com.mohamedmabrouk.letsplant.util.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

@Dao
interface UserPlantsDao {

    @Query("SELECT * FROM user_plant")
    suspend fun getAllUserPlants(): MutableList<UserPlant>

//    @Query("SELECT * FROM user_plant ORDER BY closeApproachDate DESC")
//    fun getAllAsteroids(): LiveData<List<UserPlant>>

//    @Query("SELECT * FROM user_plant WHERE closeApproachDate = :startDate ORDER BY closeApproachDate DESC")
//    fun getAsteroidsByStartDate(startDate: String): LiveData<List<DatabaseAsteroid>>
//
//    @Query("SELECT * FROM user_plant WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate DESC")
//    fun getAsteroidsByStartAndEndDate(startDate: String, endDate: String): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlant(plant: UserPlant)

    @Delete
    fun deletePlant(plant: UserPlant)

    @Query("UPDATE user_plant SET lastWateringDate = :lastDate and nextWateringDate = :nextDate WHERE id =:plantId")
    fun updatePlantWateringDates(plantId: Long?, lastDate: Date, nextDate: Date)

    @Query("UPDATE user_plant SET lastFertilizeDate = :lastDate and nextFertilizeDate = :nextDate WHERE id =:plantId")
    fun updatePlantFertilizeDates(plantId: Long?, lastDate: Date, nextDate: Date)
}

@Dao
interface RemindersDao {

    @Query("SELECT * FROM reminder WHERE date = :todayDate")
    suspend fun getTodayReminders(todayDate: Date): MutableList<Reminder>

    @Query("SELECT * FROM reminder WHERE date > :todayDate")
    suspend fun getUpcomingReminders(todayDate: Date): MutableList<Reminder>

//    @Query("SELECT * FROM user_plant ORDER BY closeApproachDate DESC")
//    fun getAllAsteroids(): LiveData<List<UserPlant>>

//    @Query("SELECT * FROM user_plant WHERE closeApproachDate = :startDate ORDER BY closeApproachDate DESC")
//    fun getAsteroidsByStartDate(startDate: String): LiveData<List<DatabaseAsteroid>>
//
//    @Query("SELECT * FROM user_plant WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate DESC")
//    fun getAsteroidsByStartAndEndDate(startDate: String, endDate: String): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminder(reminder: Reminder)

    @Delete
    fun deleteReminder(reminder: Reminder)

    @Query("DELETE FROM reminder WHERE plantId = :plantId")
    suspend fun deletePlantReminders(plantId: Long?)

//    @Query("UPDATE reminder SET status=:newStatus WHERE id = :reminderId")
//    fun updateReminderStatus(reminderId: Int, newStatus: Int)

    @Update
    fun updateReminder(reminder: Reminder)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg reminder: Reminder)

    // todo: remove all before insert
}

@Database(entities = [UserPlant::class, Reminder::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class LetsPlantDatabase : RoomDatabase() {
    abstract val userPlantsDao: UserPlantsDao
    abstract val remindersDao: RemindersDao
}

private lateinit var INSTANCE: LetsPlantDatabase

fun getDatabase(context: Context): LetsPlantDatabase {
    synchronized(LetsPlantDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                LetsPlantDatabase::class.java,
                "lets_plant_db").build()
        }
    }
    return INSTANCE
}

enum class ReminderType{
    WATERING, FERTILIZE
}

enum class ReminderStatus{
    TODAY, FUTURE, LATE, DONE
}

object DbUtils {
    suspend fun refreshReminders(letsPlantDatabase: LetsPlantDatabase) {
        withContext(Dispatchers.IO) {
            try {
                val plants = letsPlantDatabase.userPlantsDao.getAllUserPlants()
                val remindersList = ArrayList<Reminder>()

                for (plant in plants) {
                    // reminder 1: watering
                    val wateringReminderStatus: ReminderStatus =
                        when {
                            plant.nextWateringDate?.after(DateTimeUtils.getCurrentDate())!! -> ReminderStatus.FUTURE
                            plant.nextWateringDate?.before(DateTimeUtils.getCurrentDate())!! -> ReminderStatus.LATE
                            else -> ReminderStatus.TODAY
                        }
                    remindersList.add(
                        Reminder(
                            null,
                            plant.id,
                            plant.name,
                            plant.nextWateringDate,
                            ReminderType.WATERING,
                            plant.wateringRepeatCount,
                            wateringReminderStatus
                        )
                    )

                    // reminder 2: fertilize
                    val fertilizeReminderStatus: ReminderStatus =
                        when {
                            plant.nextFertilizeDate?.after(DateTimeUtils.getCurrentDate())!! -> ReminderStatus.FUTURE
                            plant.nextFertilizeDate?.before(DateTimeUtils.getCurrentDate())!! -> ReminderStatus.LATE
                            else -> ReminderStatus.TODAY
                        }
                    remindersList.add(
                        Reminder(
                            null,
                            plant.id,
                            plant.name,
                            plant.nextFertilizeDate,
                            ReminderType.FERTILIZE,
                            plant.fertilizeRepeatCount,
                            fertilizeReminderStatus
                        )
                    )
                }

                // todo: remove 0 days when adding new plants

                letsPlantDatabase.remindersDao.insertAll(*remindersList.toTypedArray())
            } catch (err: Exception) {
                Log.e("Failed", err.message.toString())
            }
        }
    }
}

//todo: check this usuful codelab
// https://developer.android.com/codelabs/basic-android-kotlin-training-project-water-me