package id.fauwiiz.gitconnect.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.fauwiiz.gitconnect.data.local.entity.UserEntity
import id.fauwiiz.gitconnect.databinding.ItemCardBinding
import id.fauwiiz.gitconnect.ui.favorite.FavoriteFragmentDirections

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var data = listOf<UserEntity>()
    fun setData(newData: List<UserEntity>){
        val diffUtil = FavoriteDiffUtil(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FavoriteViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userEntity: UserEntity) {
            binding.apply {
                tvUsarname.text = userEntity.username
                Glide.with(itemView)
                    .load(userEntity.avatarUrl)
                    .into(ivAvatar)
                root.setOnClickListener {
                    val action = FavoriteFragmentDirections.actionNavigationFavoriteToNavigationDetail(userEntity.username)
                    it.findNavController().apply {
                        navigate(action)
                   }
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int  = data.size
}

class FavoriteDiffUtil(
    private val oldList: List<UserEntity>,
    private val newList: List<UserEntity>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username == newList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].username != newList[newItemPosition].username -> {
                false
            }
            else -> true
        }
    }
}