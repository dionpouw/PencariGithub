package com.jeflette.pencarigithub.ui.detailFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeflette.pencarigithub.data.GithubUserRepository
import com.jeflette.pencarigithub.data.remote.response.DetailUserResponse
import com.jeflette.pencarigithub.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: GithubUserRepository
) : ViewModel() {
    private var _user: MutableStateFlow<Resource<DetailUserResponse>> =
        MutableStateFlow(Resource.Loading())

    val user: StateFlow<Resource<DetailUserResponse>> = _user

    fun getGithubUser(username: String) {
        viewModelScope.launch {
            repository.getDetailUser(username).collectLatest {
                _user.value = it
            }
        }
    }
}
