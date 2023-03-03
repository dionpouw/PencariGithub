package com.jeflette.pencarigithub.ui.homeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeflette.pencarigithub.data.GithubUserRepository
import com.jeflette.pencarigithub.data.remote.response.SearchUserResponse
import com.jeflette.pencarigithub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: GithubUserRepository) :
    ViewModel() {

    private var _users: MutableStateFlow<Resource<SearchUserResponse>> =
        MutableStateFlow(Resource.Loading())

    val users: StateFlow<Resource<SearchUserResponse>> = _users

    fun getGithubUser(username: String) {
        viewModelScope.launch {
            repository.getSearchUsers(username).collectLatest {
                _users.value = it
            }
        }
    }
}

