package com.jeflette.pencarigithub.ui.favoriteFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeflette.pencarigithub.data.GithubUserRepository
import com.jeflette.pencarigithub.data.local.entity.User
import com.jeflette.pencarigithub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: GithubUserRepository
) : ViewModel() {

    private var _users: MutableStateFlow<Resource<List<User>>> =
        MutableStateFlow(Resource.Loading())

    val users: StateFlow<Resource<List<User>>> = _users

    fun getUsers() {
        viewModelScope.launch {
            repository.getUsers().collect() {
                _users.value = it
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }
}