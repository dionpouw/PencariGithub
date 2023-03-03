package com.jeflette.pencarigithub.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jeflette.pencarigithub.ui.followingsFollowersFragment.FollowingsFollowersFragment

class SectionsPagerAdapter(fragment: Fragment, private val username: String) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowingsFollowersFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowingsFollowersFragment.ARG_SECTION_NUMBER, position + 1)
            putString(FollowingsFollowersFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}