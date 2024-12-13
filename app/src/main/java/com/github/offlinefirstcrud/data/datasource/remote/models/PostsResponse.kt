package com.github.offlinefirstcrud.data.datasource.remote.models

import com.google.gson.annotations.SerializedName

data class PostsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)
