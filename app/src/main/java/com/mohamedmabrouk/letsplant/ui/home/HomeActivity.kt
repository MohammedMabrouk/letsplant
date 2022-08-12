package com.mohamedmabrouk.letsplant.ui.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.firebase.FirebaseManager
import com.mohamedmabrouk.letsplant.firebase.RealtimeDbManager
import com.mohamedmabrouk.letsplant.util.LocaleHelper
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {
    private val TAG = "HomeActivity"

    @Inject
    lateinit var firebaseManager: FirebaseManager

    @Inject
    lateinit var realtimeDbManager: RealtimeDbManager

    @Inject
    lateinit var localeHelper: LocaleHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController

        setupBottomNavMenu(navController)

        firebaseManager.getDeviceToken()

        //todo: set on splash + settings
        localeHelper.setUsersLocale("ar")
    }

    // todo: add transition animation

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

//    fun obtainViewModel(): PlantsListViewModel = obtainViewModel(PlantsListViewModel::class.java)
}