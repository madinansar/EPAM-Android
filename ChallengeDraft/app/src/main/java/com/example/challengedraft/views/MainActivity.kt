package com.example.challengedraft.views

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.challengedraft.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)

        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val colorPref = sharedPref.getString("button_color", "blue")
        applyBottomNavColor(colorPref)

    }

    fun applyBottomNavColor(colorPref: String?) {
        val colorRes = when (colorPref) {
            "blue" -> R.color.blue
            "orange" -> R.color.orange
            "green" -> R.color.green
            else -> R.color.blue
        }
        val color = ContextCompat.getColor(this, colorRes)
        bottomNavigationView.setBackgroundColor(color)
    }
}