package com.camelloncase.testedeperformance03

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.camelloncase.testedeperformance03.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigation

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navController.addOnDestinationChangedListener {_, destination, _ ->
            when (destination.id) {
                R.id.navigation_login -> enablingBottomNavBar("gone")
                R.id.navigation_register -> enablingBottomNavBar("gone")
                R.id.navigation_recover -> enablingBottomNavBar("gone")
                else -> enablingBottomNavBar("visible")
            }
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_calendar, R.id.navigation_map, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun enablingBottomNavBar(state: String) {
        when (state) {
            "gone" -> binding.bottomNavigation.visibility = View.GONE
            "visible" -> binding.bottomNavigation.visibility = View.VISIBLE
        }
    }
}