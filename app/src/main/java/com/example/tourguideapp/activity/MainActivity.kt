package com.example.tourguideapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tourguideapp.R
import com.example.tourguideapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        // Set toolbar title based on destination
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home -> binding.toolbar.title = "Tour Guide App"
                R.id.nav_destinations -> binding.toolbar.title = "Destinasi Wisata"
                R.id.nav_tours -> binding.toolbar.title = "Semua Tour"
                R.id.nav_profile -> binding.toolbar.title = "Profil"
                else -> binding.toolbar.title = "" // Kosongkan judul untuk halaman lain
            }
        }

        binding.bottomNavigation.setupWithNavController(navController)
    }
} 