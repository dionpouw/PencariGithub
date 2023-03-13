package com.jeflette.pencarigithub.data.local.dao

import androidx.room.*
import com.jeflette.pencarigithub.data.local.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("DELETE FROM user where id = :id")
    suspend fun deleteUser(id: Int?)
}