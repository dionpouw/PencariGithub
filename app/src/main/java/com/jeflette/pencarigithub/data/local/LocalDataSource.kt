package com.jeflette.pencarigithub.data.local

import com.jeflette.pencarigithub.data.local.dao.UserDao
import com.jeflette.pencarigithub.data.local.entity.User
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val userDao: UserDao) {

    suspend fun insertUser(user: User) = userDao.insertUser(user)

    suspend fun deleteUser(user: User) = userDao.deleteUser(user.id)

    suspend fun getUsers() = userDao.getAllUsers()
}