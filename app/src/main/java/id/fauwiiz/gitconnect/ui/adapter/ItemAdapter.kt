package id.fauwiiz.gitconnect.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.fauwiiz.gitconnect.R
import id.fauwiiz.gitconnect.data.remote.response.User
import id.fauwiiz.gitconnect.databinding.ItemCardBinding
import id.fauwiiz.gitconnect.utils.UserDiffCallback

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var listUser = ArrayList<User>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setData(data: ArrayList<User>){
        val diffCallback = UserDiffCallback(listUser, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listUser.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }



    inner class ItemViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User) {
            with(binding){
                tvUsarname.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(ivAvatar)

//
//                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, DetailActivity::class.java)
//                    intent.putExtra(DetailActivity.EXTRA_USER, user.username)
//                    itemView.context.startActivity(intent)
//                }

                root.setOnClickListener{
                    onItemClickCallback.onItemClicked(user)
                }
            }
            val animation = AnimationUtils.loadAnimation(itemView.context, R.anim.recyclerview_anim)
            itemView.animation = animation
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemViewHolder {
        val itemCardBinding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemCardBinding)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}