package com.jeflette.pencarigithub.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jeflette.pencarigithub.R
import com.jeflette.pencarigithub.databinding.ActivityMainBinding
import com.jeflette.pencarigithub.ui.settingFragment.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: SettingViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val navHostController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.bottomNav.setupWithNavController(navHostController)
        navHostController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splashFragment) {
                binding.bottomNav.visibility = View.GONE
                binding.tvTitle.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
                binding.tvTitle.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (navHostController.currentDestination?.id == R.id.splashFragment) {
            finish()
        }
    }
}