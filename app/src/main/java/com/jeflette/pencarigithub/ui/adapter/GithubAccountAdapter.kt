package com.jeflette.pencarigithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jeflette.pencarigithub.R
import com.jeflette.pencarigithub.data.local.entity.User
import com.jeflette.pencarigithub.databinding.GithubUserItemBinding
import com.jeflette.pencarigithub.ui.detailFragment.DetailFragmentDirections
import com.jeflette.pencarigithub.ui.favoriteFragment.FavoriteFragmentDirections
import com.jeflette.pencarigithub.ui.homeFragment.HomeFragmentDirections
import com.jeflette.pencarigithub.utils.UserDiffCallback

class GithubAccountAdapter : RecyclerView.Adapter<GithubAccountAdapter.UserViewHolder>() {

    var userList = ArrayList<User>()

    fun setData(newUserList: ArrayList<User>) {
        val diffUtil = UserDiffCallback(userList, newUserList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        userList.clear()
        userList.addAll(newUserList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserViewHolder(private val itemBinding: GithubUserItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun binding(user: User) {
            itemBinding.apply {
                Glide.with(itemView.context).load(user.avatarUrl)
                    .apply(RequestOptions().override(100)).into(githubUserImage)
                githubUserName.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            GithubUserItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding(userList[position])
        holder.itemView.setOnClickListener {
            val actionHomeFragmentToDetailFragment =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(userList[position])

            val actionDetailFragmentToDetailFragment =
                DetailFragmentDirections.actionDetailFragmentSelf(
                    userList[position]
                )

            val actionFavoriteFragmentToDetailFragment =
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(
                    userList[position]
                )

            findNavController(it).currentDestination?.id?.let { id ->
                when (id) {
                    R.id.detailFragment -> findNavController(it).navigate(
                        actionDetailFragmentToDetailFragment
                    )
                    R.id.homeFragment -> findNavController(it).navigate(
                        actionHomeFragmentToDetailFragment
                    )
                    R.id.favoriteFragment -> findNavController(it).navigate(
                        actionFavoriteFragmentToDetailFragment
                    )
                }
            }
        }
    }
}