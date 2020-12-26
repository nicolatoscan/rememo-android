package com.rememo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rememo.models.Collection
import com.rememo.services.AppPreferencesHelper
import com.rememo.services.AuthenticationHelper
import com.rememo.services.api.APIWrapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var originalActionBarTitle = "Rememo"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreferencesHelper.setup(applicationContext)

        // Advanced login
        // AuthenticationHelper.logIn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InRvc2NhbiIsIl9pZCI6IjVmZDg5ZTkwNDc5YzhmMDAxNzc3YWQ2ZSIsImlhdCI6MTYwODg3OTg3NH0.OvQ9jL7OpA3R746G-VhKBSg1qSN9KG5Z_KTLmiuPkmI")

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_study,
                R.id.navigation_collections,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.selectedItemId = R.id.navigation_profile
    }

}