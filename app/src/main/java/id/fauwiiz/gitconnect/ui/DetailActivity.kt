package id.fauwiiz.gitconnect.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import id.fauwiiz.gitconnect.R
import id.fauwiiz.gitconnect.data.User
import id.fauwiiz.gitconnect.databinding.ActivityDetailBinding
import kotlin.math.ln
import kotlin.math.pow

class DetailActivity : AppCompatActivity() {
    private var _activityDetailBinding : ActivityDetailBinding ?= null
    private val binding get() = _activityDetailBinding as ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        populateDetail(user, this)


    }
    private fun populateDetail(user: User, context: Context){
        with(binding){
            tvName.text = user.name
            tvUsername.text = user.username
            tvLocation.text = user.location
            tvRepositories.text = user.repository
            tvFollowers.text = user.followers
            tvFollowing.text = user.following
            ivAvatar.setImageResource(user.avatar)

            val message = getString(R.string.share_bt, user.username)
            btShare.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, message)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(intent, null)
                startActivity(shareIntent)
            }

            btFavorite.setOnClickListener {
                val snackBar = Snackbar.make(
                    it, R.string.add_massage,
                    Snackbar.LENGTH_LONG
                ).setAction("See", null)
                snackBar.setActionTextColor(ContextCompat.getColor(context, R.color.white))
                val snackBarView = snackBar.view
                snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_secondary))
                val textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                textView.setTextColor(ContextCompat.getColor(context, R.color.white))
                snackBar.show()

            }
        }

    }


    private fun convertNumber(format: Long) : String {
        if (format < 1000) return ""+format

        val count = (ln(format.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f%c", format / 1000.0.pow(count.toDouble()), "KMBTPE"[count-1])
    }
    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }


    companion object {
        const val EXTRA_USER = "user"
    }

}