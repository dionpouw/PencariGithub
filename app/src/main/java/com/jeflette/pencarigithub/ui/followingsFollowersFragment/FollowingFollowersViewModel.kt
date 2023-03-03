package com.jeflette.pencarigithub.ui.followingsFollowersFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeflette.pencarigithub.data.GithubUserRepository
import com.jeflette.pencarigithub.data.local.entity.User
import com.jeflette.pencarigithub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingFollowersViewModel @Inject constructor(
    private val repository: GithubUserRepository
) : ViewModel() {

    private var _followingUser: MutableStateFlow<Resource<List<User>>> =
        MutableStateFlow(Resource.Loading())

    val user: StateFlow<Resource<List<User>>> = _followingUser

    fun getFollowing(username: String) {
        viewModelScope.launch {
            repository.getFollowing(username).collectLatest {
                _followingUser.value = it
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            repository.getFollowers(username).collectLatest {
                _followingUser.value = it
            }
        }
    }
}