package id.fauwiiz.gitconnect.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import id.fauwiiz.gitconnect.R
import id.fauwiiz.gitconnect.data.User
import id.fauwiiz.gitconnect.databinding.ItemCardBinding
import id.fauwiiz.gitconnect.ui.DetailActivity

class UserAdapter(private val listUser: ArrayList<User>, private val context: Context) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, context: Context) {
            with(binding){
                tvName.text = user.name
                tvUsarname.text = user.username
                ivAvatar.setImageResource(user.avatar)
                itemView.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USER, user)
                    context.startActivity(intent)
                }
                val animation = AnimationUtils.loadAnimation(context, R.anim.recyclerview_anim)
                itemView.animation = animation
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val itemCardBinding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  UserViewHolder(itemCardBinding)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user, this.context)
    }

    override fun getItemCount(): Int = listUser.size
}