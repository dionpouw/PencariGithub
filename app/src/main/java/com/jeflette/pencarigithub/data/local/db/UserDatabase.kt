package com.jeflette.pencarigithub.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeflette.pencarigithub.data.local.dao.UserDao
import com.jeflette.pencarigithub.data.local.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}