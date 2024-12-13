package com.github.offlinefirstcrud.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.offlinefirstcrud.data.datasource.remote.models.PostsResponse
import com.github.offlinefirstcrud.domain.model.PostEntity

@Entity(tableName = "posts")
data class PostDBEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
) {

    fun toResponse() = PostsResponse(id, title, body)
    fun toModel() = PostEntity(id, title, body)
}