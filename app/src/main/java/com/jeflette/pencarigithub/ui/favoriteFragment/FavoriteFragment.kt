package com.jeflette.pencarigithub.ui.favoriteFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jeflette.pencarigithub.data.local.entity.User
import com.jeflette.pencarigithub.databinding.FragmentFavoriteBinding
import com.jeflette.pencarigithub.ui.adapter.GithubAccountAdapter
import com.jeflette.pencarigithub.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModels()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userAdapter = GithubAccountAdapter()

        viewModel.getUsers()
        binding.rvUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.users.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        binding.rvUsers.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        it.data?.let { usersData ->
                            userAdapter.setData(usersData as ArrayList<User>)
                            binding.noFavoriteUser.visibility =
                                if (usersData.isEmpty()) View.VISIBLE else View.GONE
                        }
                    }
                    is Resource.Error -> {
                        binding.rvUsers.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.rvUsers.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = (viewHolder as GithubAccountAdapter.UserViewHolder).adapterPosition
                val user = userAdapter.userList[position]
                viewModel.deleteUser(user)
                userAdapter.notifyItemRemoved(position)
                userAdapter.userList.removeAt(position)
                if (userAdapter.userList.isEmpty()) {
                    binding.noFavoriteUser.visibility = View.VISIBLE
                }
                Snackbar.make(
                    binding.root,
                    "User ${user.login.toString()} has been deleted",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvUsers)
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}