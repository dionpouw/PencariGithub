package com.jeflette.pencarigithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jeflette.pencarigithub.R
import com.jeflette.pencarigithub.data.local.entity.User
import com.jeflette.pencarigithub.databinding.GithubUserItemBinding
import com.jeflette.pencarigithub.ui.detailFragment.DetailFragmentDirections
import com.jeflette.pencarigithub.ui.homeFragment.HomeFragmentDirections

class GithubAccountAdapter : RecyclerView.Adapter<GithubAccountAdapter.UserViewHolder>() {

    private val userList = mutableListOf<User>()

    fun setUserList(userList: List<User>) {
        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
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
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(userList[position].login.toString())

            val actionDetailFragmentToDetailFragment =
                DetailFragmentDirections.actionDetailFragmentSelf(
                    userList[position].login.toString()
                )

            findNavController(it).currentDestination?.id?.let { id ->
                when (id) {
                    R.id.detailFragment -> findNavController(it).navigate(
                        actionDetailFragmentToDetailFragment
                    )
                    R.id.homeFragment -> findNavController(it).navigate(
                        actionHomeFragmentToDetailFragment
                    )
                }
            }
        }
    }
}