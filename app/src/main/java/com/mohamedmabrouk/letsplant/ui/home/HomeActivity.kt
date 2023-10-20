package com.mohamedmabrouk.letsplant.ui.home

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.firebase.FirebaseManager
import com.mohamedmabrouk.letsplant.firebase.RealtimeDbManager
import com.mohamedmabrouk.letsplant.reminders.RemindersUtils
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"

    // todo : to be removed to view model
    @Inject
    lateinit var remindersUtils: RemindersUtils

    @Inject
    lateinit var firebaseManager: FirebaseManager

    @Inject
    lateinit var realtimeDbManager: RealtimeDbManager

    @Inject
    lateinit var localeHelper: LocaleHelper

    @Inject
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        setupBottomNavMenu(navController)

        firebaseManager.getDeviceToken()

//        //todo: set on splash + settings
//        localeHelper.setUsersLocale("ar")

//        val roundDialog = RoundDialog(this, null)
//        roundDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        roundDialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
//        roundDialog.show()

//        helper.createNotificationChannel()
//        helper.scheduleNotification(Reminder(
//            1,
//            null,
//            "Plant Name1",
//            Date(),
//            ReminderType.WATERING,
//            null,
//            null
//        ))
//        helper.scheduleNotification(Reminder(
//            2,
//            null,
//            "Plant Name2",
//            Date(),
//            ReminderType.FERTILIZE,
//            null,
//            null
//        ))

        remindersUtils.scheduleRefreshReminders()
    }

    // todo: add transition animation

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }


//    fun obtainViewModel(): PlantsListViewModel = obtainViewModel(PlantsListViewModel::class.java)

}
