package com.jeflette.pencarigithub.data.remote

import com.jeflette.pencarigithub.network.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getSearchUsers(username: String) = apiService.getSearchUser(username)

    suspend fun getDetailUser(username: String) = apiService.getDetailUser(username)

    suspend fun getFollowing(username: String) = apiService.getFollowing(username)

    suspend fun getFollowers(username: String) = apiService.getFollowers(username)
}