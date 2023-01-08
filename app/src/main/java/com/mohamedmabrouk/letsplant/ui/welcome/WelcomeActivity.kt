package com.mohamedmabrouk.letsplant.ui.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.mohamedmabrouk.letsplant.R
import com.mohamedmabrouk.letsplant.data.DevicePreferences
import com.mohamedmabrouk.letsplant.ui.home.HomeActivity

class WelcomeActivity : AppCompatActivity() {

    lateinit var devicePreferences: DevicePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        //todo: enable login

        devicePreferences = DevicePreferences(this)
//
        if(devicePreferences.getLoginState()){
            startActivity(Intent(this, HomeActivity::class.java))
            this.finish()
        }

//        val roundDialog = RoundDialog(this, null)
//        roundDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        roundDialog.show()
    }
}