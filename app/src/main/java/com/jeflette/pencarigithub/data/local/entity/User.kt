package com.jeflette.pencarigithub.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class User(

    @PrimaryKey @ColumnInfo(name = "id") @field:SerializedName("id") val id: Int? = null,

    @ColumnInfo(name = "user_name") @field:SerializedName("login") val login: String? = null,

    @ColumnInfo(name = "avatar_url") @field:SerializedName("avatar_url") val avatarUrl: String? = null,

    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean = false
) : Parcelable
