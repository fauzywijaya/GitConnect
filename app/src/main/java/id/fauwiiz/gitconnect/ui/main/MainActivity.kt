package id.fauwiiz.gitconnect.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.fauwiiz.gitconnect.R
import id.fauwiiz.gitconnect.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var _activityMainBinding : ActivityMainBinding ?= null
    private val binding get() = _activityMainBinding as ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.bottomBar
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

    }


    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}