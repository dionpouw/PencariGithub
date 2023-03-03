package com.jeflette.pencarigithub.network

import com.jeflette.pencarigithub.data.local.entity.User
import com.jeflette.pencarigithub.data.remote.response.DetailUserResponse
import com.jeflette.pencarigithub.data.remote.response.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): List<User>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): List<User>

    @GET("search/users")
    suspend fun getSearchUser(@Query("q") username: String): SearchUserResponse

}