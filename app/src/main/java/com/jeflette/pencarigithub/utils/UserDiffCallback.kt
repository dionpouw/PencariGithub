package com.jeflette.pencarigithub.utils

import androidx.recyclerview.widget.DiffUtil
import com.jeflette.pencarigithub.data.local.entity.User

class UserDiffCallback(
    private val oldList: ArrayList<User>,
    private val newList: ArrayList<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}