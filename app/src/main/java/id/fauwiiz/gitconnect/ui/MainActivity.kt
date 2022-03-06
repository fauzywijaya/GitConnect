package id.fauwiiz.gitconnect.ui

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.fauwiiz.gitconnect.R
import id.fauwiiz.gitconnect.data.User
import id.fauwiiz.gitconnect.databinding.ActivityMainBinding
import id.fauwiiz.gitconnect.ui.adapter.UserAdapter

class MainActivity : AppCompatActivity() {
    private var _activityMainBinding : ActivityMainBinding ?= null
    private val binding get() = _activityMainBinding as ActivityMainBinding

    private var users = arrayListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = UserAdapter(users, this)

        setData()
        with(binding.rvUser){
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    private fun  setData() {
        val names : Array<String> = resources.getStringArray(R.array.name)
        val username : Array<String> = resources.getStringArray(R.array.username)
        val avatar : TypedArray = resources.obtainTypedArray(R.array.avatar)
        val location : Array<String> = resources.getStringArray(R.array.location)
        val repository : Array<String> = resources.getStringArray(R.array.repository)
        val company : Array<String> = resources.getStringArray(R.array.company)
        val followers : Array<String> = resources.getStringArray(R.array.followers)
        val followings : Array<String> = resources.getStringArray(R.array.following)

        for (i in names.indices){
            val user = User(
                names[i],
                username[i],
                location[i],
                repository[i],
                company[i],
                followers[i],
                followings[i],
                avatar.getResourceId(i, -1)
            )
            users.add(user)
        }

        avatar.recycle()
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }
}