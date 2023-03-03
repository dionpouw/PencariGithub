package com.jeflette.pencarigithub.data.remote.response

import com.google.gson.annotations.SerializedName
import com.jeflette.pencarigithub.data.local.entity.User

data class SearchUserResponse(

    @field:SerializedName("total_count") val totalCount: Int? = null,

    @field:SerializedName("incomplete_results") val incompleteResults: Boolean? = null,

    @field:SerializedName("items") val items: List<User>? = null
)