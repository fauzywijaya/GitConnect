package id.fauwiiz.gitconnect.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.fauwiiz.gitconnect.R
import id.fauwiiz.gitconnect.data.remote.response.User
import id.fauwiiz.gitconnect.databinding.ItemCardBinding
import id.fauwiiz.gitconnect.ui.detail.DetailFragmentDirections
import id.fauwiiz.gitconnect.ui.home.HomeFragmentDirections
import id.fauwiiz.gitconnect.utils.UserDiffCallback

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var listUser = ArrayList<User>()
    private var fragmentName: String = ""

    fun setData(data: ArrayList<User>){
        val diffCallback = UserDiffCallback(listUser, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listUser.clear()
        listUser.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setFragment(name: String) {
        this.fragmentName = name
    }

    inner class UserViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, nameFragment: String) {
            with(binding){
                tvUsarname.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(ivAvatar)

                root.setOnClickListener{

                   if (nameFragment == "HomeFragment") {
                       val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(user.username)
                        it.findNavController().navigate(action)
                    }
                    else {
                        val action = DetailFragmentDirections.actionDetailFragmentSelf(user.username)
                        it.findNavController().navigate(action)
                    }
                }
                val animation = AnimationUtils.loadAnimation(itemView.context, R.anim.recyclerview_anim)
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
        holder.bind(user, this.fragmentName)
    }

    override fun getItemCount(): Int = listUser.size
}