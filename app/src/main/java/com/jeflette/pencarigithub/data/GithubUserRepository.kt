package com.jeflette.pencarigithub.data

import com.jeflette.pencarigithub.data.local.entity.User
import com.jeflette.pencarigithub.data.remote.RemoteDataSource
import com.jeflette.pencarigithub.data.remote.response.DetailUserResponse
import com.jeflette.pencarigithub.data.remote.response.SearchUserResponse
import com.jeflette.pencarigithub.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubUserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getSearchUsers(username: String): Flow<Resource<SearchUserResponse>> = flow {
        emit(Resource.Success(remoteDataSource.getSearchUsers(username)))
    }

    fun getDetailUser(username: String): Flow<Resource<DetailUserResponse>> = flow {
        emit(Resource.Success(remoteDataSource.getDetailUser(username)))
    }

    fun getFollowing(username: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Success(remoteDataSource.getFollowing(username)))
    }

    fun getFollowers(username: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Success(remoteDataSource.getFollowers(username)))
    }
}