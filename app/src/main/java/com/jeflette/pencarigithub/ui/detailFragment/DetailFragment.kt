package com.jeflette.pencarigithub.ui.detailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jeflette.pencarigithub.R
import com.jeflette.pencarigithub.databinding.FragmentDetailBinding
import com.jeflette.pencarigithub.ui.adapter.SectionsPagerAdapter
import com.jeflette.pencarigithub.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getGithubUser(args.User.login!!)
        setupViewPager()
        setupInformation()
        binding.fabFavorite.apply {
            setImageResource(R.drawable.baseline_favorite_24)
            setOnClickListener {
                viewModel.saveUser(args.User)
                Toast.makeText(context, "User saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, args.User.login!!)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setupInformation() {
        lifecycleScope.launchWhenStarted {
            viewModel.user.collectLatest {
                when (it) {
                    is Resource.Loading -> isLoading()
                    is Resource.Success -> {
                        isNotLoading()
                        binding.apply {
                            Glide.with(this@DetailFragment).load(it.data?.avatarUrl).into(imgAvatar)
                            tvUsername.text = it.data?.login
                            tvName.text = it.data?.name ?: "no username found"
                            tvLocation.text = it.data?.location ?: "no location found"
                            tvPublicRepo.text = getString(R.string.repository, it.data?.publicRepos)
                            tvFollowers.text = getString(R.string.followers, it.data?.followers)
                            tvFollowing.text = getString(R.string.following, it.data?.following)
                            tvBio.text = it.data?.bio ?: "no bio found"
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun isLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.imgAvatar.visibility = View.GONE
        binding.tvUsername.visibility = View.GONE
        binding.tvName.visibility = View.GONE
        binding.tvLocation.visibility = View.GONE
        binding.tvPublicRepo.visibility = View.GONE
        binding.tvFollowers.visibility = View.GONE
        binding.tvFollowing.visibility = View.GONE
        binding.tvBio.visibility = View.GONE
    }

    private fun isNotLoading() {
        binding.progressBar.visibility = View.GONE
        binding.imgAvatar.visibility = View.VISIBLE
        binding.tvUsername.visibility = View.VISIBLE
        binding.tvName.visibility = View.VISIBLE
        binding.tvLocation.visibility = View.VISIBLE
        binding.tvPublicRepo.visibility = View.VISIBLE
        binding.tvFollowers.visibility = View.VISIBLE
        binding.tvFollowing.visibility = View.VISIBLE
        binding.tvBio.visibility = View.VISIBLE
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }
}