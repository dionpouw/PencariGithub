package com.jeflette.pencarigithub.ui.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeflette.pencarigithub.data.local.entity.User
import com.jeflette.pencarigithub.databinding.FragmentHomeBinding
import com.jeflette.pencarigithub.ui.adapter.GithubAccountAdapter
import com.jeflette.pencarigithub.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userAdapter = GithubAccountAdapter()

        binding.rvUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        populateList(userAdapter)

        binding.apply {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        viewModel.getGithubUser(query)
                        populateList(userAdapter)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
    }

    private fun populateList(userAdapter: GithubAccountAdapter) {
        lifecycleScope.launchWhenStarted {
            viewModel.users.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        binding.rvUsers.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        it.data?.items?.let { userData ->
                            userAdapter.setData(userData as ArrayList<User>)
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
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}