package id.fauwiiz.gitconnect.utils

import androidx.recyclerview.widget.DiffUtil
import id.fauwiiz.gitconnect.data.remote.response.User

class UserDiffCallback (private val oldList: ArrayList<User>, private val newList: ArrayList<User>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int  = oldList.size

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