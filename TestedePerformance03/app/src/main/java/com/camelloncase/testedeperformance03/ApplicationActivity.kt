package com.camelloncase.testedeperformance03

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.camelloncase.testedeperformance03.databinding.ActivityApplicationBinding

class ApplicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplicationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.navHostFragmentActivityApplication.tooltipText)

        val navView: BottomNavigationView = binding.navView
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_application) as NavHostFragment
        val navController = navHost.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}