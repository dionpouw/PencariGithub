package com.jeflette.pencarigithub.ui.followingsFollowersFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeflette.pencarigithub.data.local.entity.User
import com.jeflette.pencarigithub.databinding.FragmentFollowingsFollowersBinding
import com.jeflette.pencarigithub.ui.adapter.GithubAccountAdapter
import com.jeflette.pencarigithub.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FollowingsFollowersFragment : Fragment() {

    private val viewModel: FollowingFollowersViewModel by viewModels()
    private var _binding: FragmentFollowingsFollowersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingsFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER)
        val username = arguments?.getString(ARG_USERNAME) ?: ""

        if (sectionNumber == 1) viewModel.getFollowing(username) else viewModel.getFollowers(
            username
        )
        val userAdapter = GithubAccountAdapter()

        binding.rvUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.user.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        binding.rvUsers.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        it.data?.let { users -> userAdapter.setData(users as ArrayList<User>) }
                    }
                    is Resource.Loading -> {
                        binding.rvUsers.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }
}