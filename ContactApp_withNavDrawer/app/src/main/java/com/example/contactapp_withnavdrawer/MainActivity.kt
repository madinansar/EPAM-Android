package com.example.contactapp_withnavdrawer

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.contactapp.ContactsFragment
import com.example.contactapp_withnavdrawer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, ContactsFragment())
            }
        } else {
            Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_rec -> {
                    //Toast.makeText(this, "working", Toast.LENGTH_SHORT).show()
                    checkAndRequestPermission()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true    //success
        }
    }
    private fun checkAndRequestPermission() {
        val permission = Manifest.permission.READ_CONTACTS
        if (ContextCompat.checkSelfPermission(this, permission) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, ContactsFragment())
            }
        } else {
            permissionLauncher.launch(permission)
        }
    }
}