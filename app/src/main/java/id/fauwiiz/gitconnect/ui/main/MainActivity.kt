package id.fauwiiz.gitconnect.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.fauwiiz.gitconnect.data.remote.response.User
import id.fauwiiz.gitconnect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _activityMainBinding : ActivityMainBinding ?= null
    private val binding get() = _activityMainBinding as ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}