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
        AuthenticationHelper.logIn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFuZHJvaWRlIiwiX2lkIjoiNWZlYTU5YzdlMWZhMDYwMDQ5YmY0NjNmIiwiaWF0IjoxNjA5MTkzOTI3fQ.SkzIqoZUC8ExdKv6L-UsPwKdEhxGy5v8DKxeqE258i8")

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

        var selectedPage = intent.getIntExtra("Selected page", -1)
        if (selectedPage == -1)
            selectedPage = R.id.navigation_collections
        navView.selectedItemId = selectedPage
    }

}